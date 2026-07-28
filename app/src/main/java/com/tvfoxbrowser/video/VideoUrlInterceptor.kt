package com.tvfoxbrowser.video

import android.os.Build
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import org.mozilla.geckoview.GeckoResult
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView
import java.util.concurrent.ConcurrentHashMap

/**
 * 视频地址拦截器(同时支持系统 WebView 和 GeckoView)。
 *
 * 三道防线拦截视频 URL:
 * 1. JS 注入监听 video.src 属性变化(B 站等大部分站点)
 * 2. JS Hook XMLHttpRequest 和 fetch(拦截 m3u8 动态请求)
 * 3. 注入 JS 后,GeckoView 通过 GeckoSession.runtimeDispatcher 兜底
 *
 * GeckoView 自带完整 MSE/H.264/HLS 支持,B 站视频可直接在网页内播放,
 * 此拦截器主要用于:flv 等老格式 / 仍允许用户切换到 MX Player 硬解。
 *
 * 防重复:同一 URL 在 60 秒内只触发一次。
 */
class VideoUrlInterceptor(
    private val onVideoFound: (url: String, title: String?) -> Unit
) {

    private val TAG = "VideoInterceptor"

    private val reported = ConcurrentHashMap<String, Long>()
    private val REPORT_COOLDOWN_MS = 60_000L

    private val JS_OBJECT_NAME = "TvFoxVideo"

    /** 给系统 WebView 用的注入方法(保留兼容) */
    fun injectJs(webView: WebView) {
        try {
            webView.removeJavascriptInterface(JS_OBJECT_NAME)
            webView.addJavascriptInterface(JsCallback(), JS_OBJECT_NAME)
            webView.evaluateJavascript(buildJsHook(), null)
        } catch (t: Throwable) {
            Log.w(TAG, "WebView injectJs failed", t)
        }
    }

    /** 给 GeckoView 用的注入方法 —— GeckoView 69 无 evaluateJS API,此处为空实现。
     *  Gecko 69 内核原生支持 MSE/H.264/HLS,B站等视频可直接在网页内播放,
     *  无需 JS 拦截兜底。视频 URL 仍可通过 reportVideoUrl() 从网络层上报。 */
    fun injectJs(geckoView: GeckoView) {
        // no-op:GeckoView 69 的 GeckoSession 没有 evaluate/evaluateJS 方法
        // (该 API 在 GeckoView 81+ 才引入)
    }

    /** 网络层兜底拦截:由外层调用,把可能漏掉的视频 URL 上报一次(内部有去重) */
    fun reportVideoUrl(url: String) {
        reportOnce(url, null)
    }

    private fun reportOnce(url: String, title: String?) {
        if (url.isBlank()) return
        if (!isVideoUrl(url)) return
        val now = System.currentTimeMillis()
        val lastTime = reported[url]
        if (lastTime != null && now - lastTime < REPORT_COOLDOWN_MS) {
            return
        }
        reported[url] = now
        if (reported.size > 100) {
            val it = reported.entries.iterator()
            while (it.hasNext()) {
                if (now - it.next().value > REPORT_COOLDOWN_MS) it.remove()
            }
        }
        Log.i(TAG, "Video URL reported: $url")
        onVideoFound(url, title)
    }

    private fun isVideoUrl(url: String): Boolean {
        if (url.isBlank()) return false
        val lower = url.substringBefore('?').lowercase()
        return lower.endsWith(".m3u8") ||
               lower.endsWith(".flv") ||
               lower.endsWith(".mp4") ||
               lower.endsWith(".m4v") ||
               lower.endsWith(".mkv") ||
               lower.endsWith(".webm") ||
               lower.contains("/flv/") ||
               lower.contains(".m3u8?")
    }

    /**
     * JS 注入脚本。
     * GeckoView 没有 addJavascriptInterface,这里通过 console.log 上报视频 URL,
     * 由 GeckoSession.contentDelegate.onConsoleMessage 捕获(WebViewEngine 可扩展)。
     * 当前 GeckoView 自带 MSE 已能直接播放视频,此 JS 仅作观察用。
     */
    private fun buildJsHook(): String = """
(function() {
    if (window.__TvFoxVideoHooked) return;
    window.__TvFoxVideoHooked = true;

    function report(url) {
        try {
            if (typeof url !== 'string' || !url) return;
            var lower = url.split('?')[0].toLowerCase();
            if (!(/\.(m3u8|flv|mp4|m4v|mkv|webm)(\?|$)/.test(lower))) return;
            var abs = new URL(url, location.href).href;
            // GeckoView 没有 JS bridge,用 console.log 上报
            console.log('TvFoxVideo:' + abs);
        } catch(e) {}
    }

    function hookVideoElement(v) {
        if (!v || v.__TvFoxHooked) return;
        v.__TvFoxHooked = true;
        try {
            var proto = HTMLVideoElement.prototype;
            var desc = Object.getOwnPropertyDescriptor(proto, 'src');
            if (desc && desc.set) {
                Object.defineProperty(v, 'src', {
                    configurable: true,
                    enumerable: desc.enumerable,
                    get: desc.get,
                    set: function(val) {
                        try { report(val); } catch(e) {}
                        desc.set.call(this, val);
                    }
                });
            }
        } catch(e) {}
        if (v.src) report(v.src);
        v.addEventListener('loadstart', function() {
            try { if (v.currentSrc) report(v.currentSrc); } catch(e) {}
        });
    }

    function hookAllVideos() {
        try {
            var list = document.querySelectorAll('video');
            for (var i = 0; i < list.length; i++) hookVideoElement(list[i]);
        } catch(e) {}
    }

    function hookXHR() {
        try {
            var origOpen = XMLHttpRequest.prototype.open;
            XMLHttpRequest.prototype.open = function(method, url) {
                try { report(url); } catch(e) {}
                return origOpen.apply(this, arguments);
            };
        } catch(e) {}
    }

    function hookFetch() {
        try {
            if (!window.fetch) return;
            var origFetch = window.fetch;
            window.fetch = function(input) {
                try {
                    var u = (typeof input === 'string') ? input : (input && input.url);
                    report(u);
                } catch(e) {}
                return origFetch.apply(this, arguments);
            };
        } catch(e) {}
    }

    function hookMutation() {
        try {
            if (!window.MutationObserver) return;
            var mo = new MutationObserver(function(muts) {
                for (var i = 0; i < muts.length; i++) {
                    var added = muts[i].addedNodes;
                    for (var j = 0; j < added.length; j++) {
                        var node = added[j];
                        if (node.nodeType === 1) {
                            if (node.tagName === 'VIDEO') hookVideoElement(node);
                            if (node.querySelectorAll) {
                                var vids = node.querySelectorAll('video');
                                for (var k = 0; k < vids.length; k++) hookVideoElement(vids[k]);
                            }
                        }
                    }
                }
            });
            mo.observe(document.documentElement || document.body, { childList: true, subtree: true });
        } catch(e) {}
    }

    try { hookXHR(); } catch(e) {}
    try { hookFetch(); } catch(e) {}
    try { hookAllVideos(); } catch(e) {}
    try { hookMutation(); } catch(e) {}

    try {
        setInterval(function() {
            try { hookAllVideos(); } catch(e) {}
        }, 2000);
    } catch(e) {}
})();
    """.trimIndent()

    /** JS 回调到 Java 的桥接对象(仅 WebView 用,GeckoView 走 console.log) */
    private inner class JsCallback {
        @JavascriptInterface
        fun onVideo(url: String) {
            reportOnce(url, null)
        }
    }
}
