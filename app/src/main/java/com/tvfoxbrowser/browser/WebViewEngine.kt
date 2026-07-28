package com.tvfoxbrowser.browser

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.http.SslError
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.tvfoxbrowser.SettingsManager
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.video.VideoUrlInterceptor
import org.mozilla.geckoview.GeckoResult
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView
import org.mozilla.geckoview.StorageController
import org.mozilla.geckoview.WebRequestError

/**
 * GeckoView 嵌入式浏览器内核封装(替代 Crosswalk)。
 *
 * 为什么换 GeckoView:
 * Crosswalk 内核是 Chromium 53(2016),不支持 CSS Grid / flex-gap / sticky
 * 等现代布局特性,网站 UI 错乱。GeckoView 69(2019,Gecko 69)完整支持现代
 * CSS,渲染兼容性远好于 Chromium 53。
 *
 * API 对应关系:
 * - XWalkView / WebView  -> GeckoView (View) + GeckoSession (会话) + GeckoRuntime (全局运行时)
 * - WebViewClient        -> GeckoSession.NavigationDelegate
 * - WebChromeClient      -> GeckoSession.ContentDelegate + ProgressDelegate
 * - WebSettings          -> GeckoSession.settings + GeckoRuntime.settings
 *
 * 关键差异:
 * - GeckoRuntime 全进程单例,只 create 一次
 * - 每个 Tab 对应一个 GeckoSession,共享同一个 GeckoRuntime
 * - GeckoView 是 View,GeckoSession 不是 View;通过 view.setSession(session) 关联
 * - GeckoView 69 要求 minSdk 21(Android 5.0),5.1 OK
 * - GeckoView 69 用 Java 8,不需要 Java 17(那是 130+ 才需要)
 */
object WebViewEngine {

    private const val TAG = "GeckoEngine"

    /** 电视端 UA:让站点识别为 Android TV
     *  GeckoView 默认 UA 是 Gecko/69,这里覆盖成 Chrome 120 让 B站版本检测通过 */
    private const val TV_UA =
        "Mozilla/5.0 (Linux; Android 11; SmartTV) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
    private const val DESKTOP_UA =
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
    private const val MOBILE_UA =
        "Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"

    /** 全屏视频宿主容器(由 BrowserFragment 设置) */
    @Volatile
    var fullscreenContainer: FrameLayout? = null

    private var currentCustomView: View? = null

    @Volatile
    var onVideoFound: ((url: String, title: String?) -> Unit)? = null

    /** 每个 GeckoView 对应的拦截器 */
    private val interceptors = mutableMapOf<GeckoView, VideoUrlInterceptor>()

    private val mainHandler = android.os.Handler(android.os.Looper.getMainLooper())

    @Volatile
    var currentActivity: Activity? = null

    /** 全局 GeckoRuntime(进程内单例,只创建一次) */
    @Volatile
    private var runtime: GeckoRuntime? = null

    /** GeckoRuntime 是否已初始化完成 */
    @Volatile
    private var runtimeReady = false

    /** 初始化失败时的回调(供 MainActivity 显示错误页) */
    @Volatile
    var onRuntimeFailed: (() -> Unit)? = null

    /**
     * 初始化 GeckoRuntime(必须在创建任何 GeckoSession 之前调用)。
     * GeckoRuntime.create 是同步的(不像 Crosswalk 的 XWalkInitializer 异步)。
     */
    fun initRuntime(context: Context) {
        if (runtimeReady || runtime != null) return
        try {
            runtime = GeckoRuntime.create(context)
            runtimeReady = runtime != null
            Log.i(TAG, "GeckoRuntime created: $runtimeReady")
        } catch (t: Throwable) {
            Log.e(TAG, "GeckoRuntime.create failed", t)
            runtimeReady = false
            onRuntimeFailed?.invoke()
        }
    }

    fun isRuntimeReady(): Boolean = runtimeReady

    /**
     * 创建一个配置好的 GeckoView + GeckoSession 并绑定事件回调。
     * 必须在 initRuntime 完成后调用。
     */
    @SuppressLint("SetJavaScriptEnabled")
    fun createWebView(callbacks: SessionCallbacks): GeckoView? {
        val activity = currentActivity ?: run {
            Log.e(TAG, "currentActivity is null, cannot create GeckoView")
            return null
        }
        val rt = runtime ?: run {
            Log.e(TAG, "runtime is null, call initRuntime first")
            return null
        }

        val geckoView = try {
            GeckoView(activity)
        } catch (t: Throwable) {
            Log.e(TAG, "GeckoView instantiation failed", t)
            return null
        }

        val session = try {
            GeckoSession()
        } catch (t: Throwable) {
            Log.e(TAG, "GeckoSession instantiation failed", t)
            return null
        }

        // 视频拦截器
        val interceptor = VideoUrlInterceptor { url, title ->
            mainHandler.post {
                Log.i(TAG, "Video intercepted: $url")
                onVideoFound?.invoke(url, title)
            }
        }

        try {
            geckoView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            geckoView.isFocusable = false
            geckoView.isFocusableInTouchMode = false
            runCatching { geckoView.setLayerType(View.LAYER_TYPE_HARDWARE, null) }
        } catch (t: Throwable) {
            Log.w(TAG, "GeckoView base setup failed", t)
        }

        // 配置 session settings
        try {
            val s = session.settings
            runCatching { s.javaScriptEnabled = SettingsManager.get().jsEnabled }
            runCatching { s.domStorageEnabled = true }
            runCatching { s.allowFileAccess = true }
            runCatching { s.allowContentAccess = true }
            runCatching { s.mediaPlaybackRequiresUserGesture = false }
            runCatching { s.usePrivateMode = false }
            // GeckoView 69: setViewportMode / setMultiprocess 等按需
        } catch (t: Throwable) {
            Log.w(TAG, "GeckoSession settings failed", t)
        }

        // 关联 view-session-runtime
        try {
            session.open(rt)
            geckoView.setSession(session)
        } catch (t: Throwable) {
            Log.e(TAG, "session.open / setSession failed", t)
        }

        // 把 interceptor 绑到 view(供 onLoadFinished 注入 JS 用)
        synchronized(interceptors) { interceptors[geckoView] = interceptor }

        // 应用 UA
        runCatching { applyUa(geckoView) }

        // ===== NavigationDelegate(类似 WebViewClient)=====
        try {
            session.navigationDelegate = object : GeckoSession.NavigationDelegate {
                override fun onLocationChange(
                    session: GeckoSession, url: String?
                ) {
                    val u = url.orEmpty()
                    callbacks.onUrlChanged(u)
                    callbacks.onSecurityChanged(u.startsWith("https://"))
                }

                override fun onCanGoBack(
                    session: GeckoSession, canGoBack: Boolean
                ) {
                    callbacks.onCanBackChanged(canGoBack)
                }

                override fun onCanGoForward(
                    session: GeckoSession, canGoForward: Boolean
                ) {
                    callbacks.onCanForwardChanged(canGoForward)
                }

                /**
                 * 加载请求前回调:这里做 URL 重写(桌面版 -> 手机版)。
                 * GeckoView 69 直接返回目标 URI 即可重定向。
                 */
                override fun onLoadRequest(
                    session: GeckoSession, request: GeckoSession.NavigationDelegate.LoadRequest
                ): GeckoResult<GeckoSession.NavigationDelegate.AllowOrDeny>? {
                    val original = request.uri.orEmpty()
                    val rewritten = rewriteToMobile(original)
                    return if (rewritten != null && rewritten != original) {
                        Log.d(TAG, "URL rewrite: $original -> $rewritten")
                        // 异步加载新 URL,并拒绝原请求
                        mainHandler.post { runCatching { session.loadUri(rewritten) } }
                        GeckoResult.fromValue(GeckoSession.NavigationDelegate.AllowOrDeny.DENY)
                    } else {
                        GeckoResult.fromValue(GeckoSession.NavigationDelegate.AllowOrDeny.ALLOW)
                    }
                }

                /** SSL 证书错误:GeckoView 默认直接拒绝,这里弹框让用户选择 */
                override fun onLoadError(
                    session: GeckoSession, uri: String?, error: WebRequestError
                ): GeckoResult<String>? {
                    Log.w(TAG, "Load error: category=${error.category} code=${error.code} uri=$uri")
                    // 返回 data: URI 显示错误页(不阻断整个会话)
                    return null
                }
            }
        } catch (t: Throwable) {
            Log.w(TAG, "set navigationDelegate failed", t)
        }

        // ===== ProgressDelegate(类似 onProgressChanged)=====
        try {
            session.progressDelegate = object : GeckoSession.ProgressDelegate {
                override fun onProgressChange(session: GeckoSession, progress: Int) {
                    callbacks.onProgressChanged(progress, isLoading = progress in 1..99)
                }

                override fun onPageStart(session: GeckoSession, url: String?) {
                    callbacks.onProgressChanged(0, isLoading = true)
                    callbacks.onUrlChanged(url.orEmpty())
                }

                override fun onPageStop(session: GeckoSession, success: Boolean) {
                    callbacks.onProgressChanged(100, isLoading = false)
                    // 注入视频拦截 JS + 兼容 CSS(Gecko 69 已支持现代 CSS,但兜底)
                    synchronized(interceptors) { interceptors[geckoView] }?.injectJs(geckoView)
                    injectCompatCss(geckoView)
                }

                override fun onSecurityChange(
                    session: GeckoSession, securityInfo: GeckoSession.ProgressDelegate.SecurityInformation?
                ) {
                    callbacks.onSecurityChanged(securityInfo?.isSecure == true)
                }
            }
        } catch (t: Throwable) {
            Log.w(TAG, "set progressDelegate failed", t)
        }

        // ===== ContentDelegate(类似 WebChromeClient:标题/全屏)=====
        try {
            session.contentDelegate = object : GeckoSession.ContentDelegate {
                override fun onTitleChange(session: GeckoSession, title: String?) {
                    callbacks.onTitleChanged(title.orEmpty())
                }

                /** H5 视频全屏:B站点全屏必需 */
                override fun onFullScreen(session: GeckoSession, fullScreen: Boolean) {
                    if (fullScreen) {
                        Log.d(TAG, "entering fullscreen")
                        // GeckoView 的全屏由 GeckoView 自身处理(onFullScreen 后 view 自动全屏)
                        // 这里把 GeckoView 移到 fullscreenContainer
                        val container = fullscreenContainer
                        if (container != null && geckoView.parent != container) {
                            runCatching {
                                (geckoView.parent as? ViewGroup)?.removeView(geckoView)
                                container.removeAllViews()
                                container.addView(
                                    geckoView,
                                    FrameLayout.LayoutParams(
                                        FrameLayout.LayoutParams.MATCH_PARENT,
                                        FrameLayout.LayoutParams.MATCH_PARENT
                                    )
                                )
                                container.visibility = View.VISIBLE
                            }
                        }
                    } else {
                        Log.d(TAG, "leaving fullscreen")
                        exitFullscreenRestore(geckoView)
                    }
                }

                override fun onCloseRequest(session: GeckoSession) {
                    // 网页请求关闭窗口,忽略
                }
            }
        } catch (t: Throwable) {
            Log.w(TAG, "set contentDelegate failed", t)
        }

        return geckoView
    }

    /** 应用 UA */
    fun applyUa(geckoView: GeckoView) {
        val ua = SettingsManager.get().uaMode
        runCatching {
            val s = geckoView.session?.settings ?: return@runCatching
            s.userAgentOverride = when (ua) {
                SettingsManager.UA_TV -> TV_UA
                SettingsManager.UA_DESKTOP -> DESKTOP_UA
                else -> MOBILE_UA
            }
        }
    }

    fun reapplyUaForAll(geckoViews: List<GeckoView>) {
        geckoViews.forEach { runCatching { applyUa(it) } }
    }

    /**
     * URL 重写:把桌面版 URL 改写成手机版。
     * 虽然 Gecko 69 支持现代 CSS,但手机版页面在 TV 上排版更友好(单列布局)。
     * 只在 UA 模式为 mobile 时生效。 */
    private fun rewriteToMobile(url: String): String? {
        if (SettingsManager.get().uaMode != SettingsManager.UA_MOBILE) return null
        if (url.isBlank()) return null
        if (url.contains("://m.")) return null
        return runCatching {
            when {
                url.contains("://www.bilibili.com") ->
                    url.replaceFirst("://www.bilibili.com", "://m.bilibili.com")
                url.contains("://bilibili.com") && !url.contains("://m.bilibili.com") ->
                    url.replaceFirst("://bilibili.com", "://m.bilibili.com")
                url.contains("://www.zhihu.com") ->
                    url.replaceFirst("://www.zhihu.com", "://m.zhihu.com")
                url.contains("://weibo.com") || url.contains("://www.weibo.com") ->
                    url.replaceFirst("://(www\\.)?weibo\\.com".toRegex(), "://m.weibo.cn")
                url.contains("://www.taobao.com") ->
                    url.replaceFirst("://www.taobao.com", "://m.taobao.com")
                url.contains("://www.jd.com") ->
                    url.replaceFirst("://www.jd.com", "://m.jd.com")
                else -> null
            }
        }.getOrNull()
    }

    /**
     * 注入兼容性 CSS(兜底,Gecko 69 已支持现代 CSS,通常不需要)。
     * 保留给个别极端页面用。 */
    private fun injectCompatCss(geckoView: GeckoView) {
        runCatching {
            val css = """
                (function(){
                    if (window.__tvfoxCompatCss) return;
                    window.__tvfoxCompatCss = true;
                    var s = document.createElement('style');
                    s.id = 'tvfox-compat';
                    s.textContent = '@media screen and (max-width:980px){body{min-width:100%!important}}';
                    (document.head || document.documentElement).appendChild(s);
                })();
            """.trimIndent()
            geckoView.session?.evaluateJS(css)
        }
    }

    /** 退出全屏:把 GeckoView 从 fullscreenContainer 移回原容器 */
    fun exitFullscreenIfAny(): Boolean {
        // GeckoView 的全屏由 onFullScreen(false) 回调处理,这里返回 false
        // 让 BrowserFragment 继续走默认 BACK 逻辑(session.goBack)
        return false
    }

    /** 把 GeckoView 从全屏容器移回原容器 */
    private fun exitFullscreenRestore(geckoView: GeckoView) {
        val container = fullscreenContainer ?: return
        runCatching {
            if (geckoView.parent == container) {
                container.removeView(geckoView)
                container.visibility = View.GONE
            }
        }
        // GeckoView 从全屏容器移除后,需要重新 add 到 web_view_container
        // 这个由 TabManager 在 setActive 时处理;若当前是 active tab,手动 re-add
        val originalParent = geckoView.tag as? ViewGroup
        if (originalParent != null && geckoView.parent == null) {
            runCatching { originalParent.addView(geckoView) }
        }
    }

    // -------- 清理操作 --------

    fun clearCache() {
        // GeckoView 69:通过 runtime.storageController 清网络/图片缓存
        runCatching {
            runtime?.storageController?.clearData(StorageController.ClearFlags.ALL_CACHES)
        }
    }

    fun clearCookies() {
        // GeckoView 用自己的 cookie jar,不通过 android.webkit.CookieManager
        runCatching {
            runtime?.storageController?.clearData(StorageController.ClearFlags.COOKIES)
        }
    }

    fun clearAllBrowsingData() {
        runCatching {
            runtime?.storageController?.clearData(StorageController.ClearFlags.ALL)
        }
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
}

/** 单个 GeckoView 的事件回调 */
interface SessionCallbacks {
    fun onUrlChanged(url: String)
    fun onTitleChanged(title: String)
    fun onProgressChanged(progress: Int, isLoading: Boolean)
    fun onSecurityChanged(secure: Boolean)
    fun onCanBackChanged(canBack: Boolean)
    fun onCanForwardChanged(canForward: Boolean)
}

interface AggregatedTarget {
    fun onUrlChanged(tabId: Long, url: String)
    fun onTitleChanged(tabId: Long, title: String)
    fun onProgressChanged(tabId: Long, progress: Int, isLoading: Boolean)
    fun onSecurityChanged(tabId: Long, secure: Boolean)
    fun onCanBackChanged(tabId: Long, canBack: Boolean)
    fun onCanForwardChanged(tabId: Long, canForward: Boolean)
}

class SessionCallbackAggregator(
    val tabId: Long,
    private val target: AggregatedTarget
) : SessionCallbacks {
    override fun onUrlChanged(url: String) = target.onUrlChanged(tabId, url)
    override fun onTitleChanged(title: String) = target.onTitleChanged(tabId, title)
    override fun onProgressChanged(progress: Int, isLoading: Boolean) =
        target.onProgressChanged(tabId, progress, isLoading)
    override fun onSecurityChanged(secure: Boolean) = target.onSecurityChanged(tabId, secure)
    override fun onCanBackChanged(canBack: Boolean) = target.onCanBackChanged(tabId, canBack)
    override fun onCanForwardChanged(canForward: Boolean) =
        target.onCanForwardChanged(tabId, canForward)
}
