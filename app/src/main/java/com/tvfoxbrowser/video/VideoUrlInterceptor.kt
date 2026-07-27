package com.tvfoxbrowser.video

import android.os.Build
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import java.util.concurrent.ConcurrentHashMap

/**
 * 视频地址拦截器。
 *
 * 三道防线拦截视频 URL:
 * 1. JS 注入监听 video.src 属性变化(B 站等大部分站点)
 * 2. JS Hook XMLHttpRequest 和 fetch(拦截 m3u8 动态请求)
 * 3. shouldInterceptRequest 网络层兜底
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
     * 在 onPageFinished 注入 JS,监听 video.src 和 XHR/fetch。
     *
     * 注入时机选 onPageFinished 而非 onPageStarted:
     * - onPageStarted 时 DOM 还没构建完,querySelector 找不到 video
     * - 注入太早可能被站点 CSP 拦截
     */
    fun injectJs(webView: WebView) {
        // 1. 注册 JS 接口(Android 4.2+ 的 @JavascriptInterface 安全机制)
        // 注意:addJavascriptInterface 必须在主线程,且一个 WebView 只能注册一次同名对象
        try {
            webView.removeJavascriptInterface(JS_OBJECT_NAME)
            webView.addJavascriptInterface(JsCallback(), JS_OBJECT_NAME)
        } catch (t: Throwable) {
            Log.w(TAG, "addJavascriptInterface failed", t)
        }

        // 2. 注入监听脚本
        val js = buildJsHook()
        try {
            webView.evaluateJavascript(js, null)
            Log.d(TAG, "JS hook injected")
        } catch (t: Throwable) {
            // Android 4.4 以下没有 evaluateJavascript,用 loadURL("javascript:") 兜底
            Log.w(TAG, "evaluateJavascript failed, fallback to loadUrl", t)
            try {
                webView.loadUrl("javascript:" + js)
            } catch (_: Throwable) {}
        }
    }

    /**
     * 网络层兜底拦截:WebResourceRequest 是网络层所有请求都会经过的。
     * 注意:只在 API 21+ 的 shouldInterceptRequest 里调用。
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun maybeInterceptRequest(request: WebResourceRequest): WebResourceResponse? {
        try {
            val url = request.url?.toString() ?: return null
            if (isVideoUrl(url)) {
                Log.d(TAG, "Network layer caught video URL: $url")
                onVideoFound(url, null)
            }
        } catch (_: Throwable) {}
        // 永远返回 null:不真正拦截请求,只观察
        // 真正拦截会导致 B 站播放器卡死,我们要的是"边播边报"
        return null
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

    /**
     * JS 注入脚本。
     * 关键点:
     * - 用 Object.defineProperty 劫持 video.src 的 setter
     * - Hook XMLHttpRequest.open 和 fetch
     * - MutationObserver 监听新插入的 video 标签(动态加载的视频)
     * - try/catch 包裹,任一步失败不影响页面
     */
    private fun buildJsHook(): String = """
(function() {
    if (window.__TvFoxVideoHooked) return;
    window.__TvFoxVideoHooked = true;

    function report(url) {
        try {
            if (typeof url !== 'string' || !url) return;
            // 过滤明显不是视频的
            var lower = url.split('?')[0].toLowerCase();
            if (!(/\.(m3u8|flv|mp4|m4v|mkv|webm)(\?|$)/.test(lower))) return;
            // 相对路径转绝对
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
        // 已存在的 src
        if (v.src) report(v.src);
        // 监听 source 标签变化
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

    // B 站等 SPA 站点会在路由切换后延迟插入 video,定时轮询兜底
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
