package com.tvfoxbrowser.video

import android.os.Build
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import org.xwalk.core.XWalkView
import java.util.concurrent.ConcurrentHashMap

/**
 * 视频地址拦截器(同时支持系统 WebView 和 Crosswalk XWalkView)。
 *
 * 三道防线拦截视频 URL:
 * 1. JS 注入监听 video.src 属性变化(B 站等大部分站点)
 * 2. JS Hook XMLHttpRequest 和 fetch(拦截 m3u8 动态请求)
 * 3. shouldInterceptRequest 网络层兜底(由调用方在外层实现)
 *
 * 拦截到后回调 onVideoFound,由 BrowserFragment 调起 ExternalPlayerLauncher。
 *
 * 防重复:同一 URL 在 60 秒内只触发一次(避免页面内多个 video 元素重复触发)
 */
class VideoUrlInterceptor(
    private val onVideoFound: (url: String, title: String?) -> Unit
) {

    private val TAG = "VideoInterceptor"

    /** 已上报过的视频 URL(防止短时间内重复触发) */
    private val reported = ConcurrentHashMap<String, Long>()
    private val REPORT_COOLDOWN_MS = 60_000L  // 60 秒内同 URL 只触发一次

    /** JS 注入到页面后暴露的全局对象名: window.TvFoxVideo */
    private val JS_OBJECT_NAME = "TvFoxVideo"

    /**
     * 在页面加载完成后注入 JS,监听 video.src 和 XHR/fetch。
     * 同时支持系统 WebView 和 Crosswalk XWalkView(都用同样的 JS API)。
     */
    fun injectJs(webView: WebView) {
        try {
            webView.removeJavascriptInterface(JS_OBJECT_NAME)
            webView.addJavascriptInterface(JsCallback(), JS_OBJECT_NAME)
            webView.evaluateJavascript(buildJsHook(), null)
        } catch (t: Throwable) {
            Log.w(TAG, "WebView injectJs failed", t)
        }
    }

    /** 给 XWalkView 用的注入方法 */
    fun injectJs(xWalkView: XWalkView) {
        try {
            // XWalkView 的 addJavascriptInterface 与 WebView 等价
            xWalkView.addJavascriptInterface(JsCallback(), JS_OBJECT_NAME)
            // XWalkView.evaluateJavascript 返回值通过 ValueCallback
            xWalkView.evaluateJavascript(buildJsHook()) { /* 忽略结果 */ }
        } catch (t: Throwable) {
            Log.w(TAG, "XWalkView injectJs failed", t)
        }
    }

    /**
     * 网络层兜底拦截:由外层 WebViewClient/XWalkResourceClient 调用,
     * 把可能漏掉的视频 URL 上报一次(内部有去重)。
     */
    fun reportVideoUrl(url: String) {
        reportOnce(url, null)
    }

    /** 去重后回调 */
    private fun reportOnce(url: String, title: String?) {
        if (url.isBlank()) return
        if (!isVideoUrl(url)) return
        val now = System.currentTimeMillis()
        val lastTime = reported[url]
        if (lastTime != null && now - lastTime < REPORT_COOLDOWN_MS) {
            return  // 冷却中,跳过
        }
        reported[url] = now
        // 清理过期项(防止内存泄漏)
        if (reported.size > 100) {
            val it = reported.entries.iterator()
            while (it.hasNext()) {
                if (now - it.next().value > REPORT_COOLDOWN_MS) it.remove()
            }
        }
        Log.i(TAG, "Video URL reported: $url")
        onVideoFound(url, title)
    }

    /** 判断 URL 是否是视频地址 */
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
     * 关键点:
     * - 用 Object.defineProperty 劫持 video.src 的 setter
     * - Hook XMLHttpRequest.open 和 fetch
     * - MutationObserver 监听新插入的 video 标签
     * - try/catch 包裹,任一步失败不影响页面
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
            window.TvFoxVideo && window.TvFoxVideo.onVideo(abs);
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

    /** JS 回调到 Java 的桥接对象 */
    private inner class JsCallback {
        @JavascriptInterface
        fun onVideo(url: String) {
            reportOnce(url, null)
        }
    }
}
