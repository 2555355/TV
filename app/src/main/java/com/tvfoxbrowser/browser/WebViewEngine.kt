package com.tvfoxbrowser.browser

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.tvfoxbrowser.SettingsManager

/**
 * 系统 WebView 内核封装(替代 GeckoView)。
 *
 * 为什么换 WebView:
 * GeckoView 124 要求 2GB+ 内存,低端电视盒(434MB 可用)会 OOM 闪退。
 * 系统 WebView 内存占用仅 ~20MB,且复用系统 native 库,APK 体积大幅缩小。
 *
 * 设计:
 * - 每个 Tab 持有独立的 WebView 实例(支持多标签独立状态)
 * - 通过 [createWebView] 创建配置好的 WebView 并绑定回调
 * - UA 三档:mobile / desktop / tv(tv 用自定义 UA 让站点返回 TV 适配页)
 *
 * 海尔 HRA920L (Android 5.1, API 22) 兼容性:
 * - 所有 WebSettings 调用都用 try-catch 包裹,ROM 阉割/老内核缺少某些 API 时
 *   不要让进程崩溃,只是该特性不可用。
 * - createWebView 整体失败时返回 null,由 TabManager 决定如何降级。
 *
 * B站/视频站适配:
 * - onShowCustomView/onHideCustomView:支持 H5 全屏视频(否则点全屏无反应)
 * - onJsAlert/onJsConfirm/onJsPrompt:支持 JS 交互(否则某些站点卡死)
 * - onReceivedSslError:SSL 证书错误时弹框让用户决定,而非静默失败
 * - onReceivedError/onReceivedHttpError:把错误信息打到 logcat,便于排查
 */
object WebViewEngine {

    private const val TAG = "WebViewEngine"

    /** 电视端 UA:让站点识别为 Android TV */
    private const val TV_UA =
        "Mozilla/5.0 (Linux; Android 11; SmartTV) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"

    private const val DESKTOP_UA =
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"

    /** 当前全屏视频宿主容器(由 BrowserFragment 设置,用于 WebChromeClient.onShowCustomView) */
    @Volatile
    var fullscreenContainer: FrameLayout? = null

    /** 当前正在全屏显示的 View(退出全屏时需要从容器移除) */
    private var currentCustomView: View? = null
    private var currentCustomViewCallback: WebChromeClient.CustomViewCallback? = null

    /**
     * 检测系统 WebView 是否可用。
     * 国产电视 ROM 偶尔会阉割 WebView 包(/system/app/webview 缺失或被禁用),
     * 此时创建 WebView 会抛 ClassNotFoundException / Resources$NotFoundException。
     */
    fun isWebViewAvailable(context: Context): Boolean = try {
        WebView.getCurrentWebViewPackage() != null
    } catch (t: Throwable) {
        // API < 26 没有 getCurrentWebViewPackage;且早期 ROM 可能不返回,做最终保险:
        try {
            WebView(context)
            true
        } catch (_: Throwable) {
            false
        }
    }

    /**
     * 创建一个配置好的 WebView 并绑定事件回调。
     * 调用方负责把返回的 WebView 加入/移出容器视图。
     *
     * @return 配置好的 WebView;若系统 WebView 不可用或初始化失败,返回 null
     *         (调用方需自行降级,不能让进程崩溃)
     */
    @SuppressLint("SetJavaScriptEnabled")
    fun createWebView(callbacks: SessionCallbacks): WebView? {
        val context = runCatching { com.tvfoxbrowser.TvFoxApp.getApp() }.getOrNull()
            ?: return null

        val webView = try {
            WebView(context)
        } catch (t: Throwable) {
            // 国产 ROM 上 WebView 包被阉割时会在此抛异常
            Log.e(TAG, "WebView instantiation failed", t)
            return null
        }

        try {
            webView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webView.isFocusable = false
            webView.isFocusableInTouchMode = false
            // 启用硬件加速层(视频/动画必须),API 14+ 默认硬件,显式设置保险
            runCatching { webView.setLayerType(View.LAYER_TYPE_HARDWARE, null) }
        } catch (t: Throwable) {
            Log.w(TAG, "WebView base setup failed", t)
        }

        // WebSettings 每个属性都单独包 try-catch:
        // 老 WebView 内核缺少某些 API setter 时,只跳过该项,不致命。
        try {
            with(webView.settings) {
                runCatching { javaScriptEnabled = SettingsManager.get().jsEnabled }
                runCatching { domStorageEnabled = true }       // localStorage/sessionStorage,B站必须
                runCatching { databaseEnabled = true }         // WebSQL/IndexedDB 老接口
                runCatching { useWideViewPort = true }
                runCatching { loadWithOverviewMode = true }
                runCatching { cacheMode = WebSettings.LOAD_DEFAULT }
                runCatching { builtInZoomControls = false }
                runCatching { displayZoomControls = false }
                runCatching { setSupportZoom(false) }
                runCatching { mediaPlaybackRequiresUserGesture = false } // 自动播放,视频站必需
                // 老站点文件/内容访问
                runCatching { allowFileAccess = true }
                runCatching { allowContentAccess = true }
                // AppCache:Android 5.1 老 WebView 必须显式开,否则部分站点资源加载失败。
                // 但 setAppCacheEnabled/setAppCachePath 在 compileSdk 26 弃用,
                // compileSdk 34 已被 Kotlin 编译器彻底移除,这里用反射调用。
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    runCatching {
                        val cls = WebSettings::class.java
                        cls.getMethod("setAppCacheEnabled", java.lang.Boolean.TYPE)
                            .invoke(this, true)
                        cls.getMethod("setAppCachePath", String::class.java)
                            .invoke(this, context.cacheDir.absolutePath)
                    }
                }
                // 允许混合内容(部分老站点 http 资源),API 21+
                // 注意:MIXED_CONTENT_ALWAYS_ALLOW 比 COMPATIBILITY_MODE 更宽松,
                // 国产 ROM 老 WebView 上后者偶尔仍会拒绝 http 子资源,改用 ALLOW 确保 B 站图片 CDN 能出
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    runCatching { mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW }
                }
            }
        } catch (t: Throwable) {
            Log.w(TAG, "WebSettings bulk setup failed", t)
        }

        runCatching { applyUa(webView) }

        // Cookie
        runCatching {
            CookieManager.getInstance().setAcceptCookie(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
            }
        }

        try {
            webView.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    callbacks.onProgressChanged(0, isLoading = true)
                    callbacks.onUrlChanged(url.orEmpty())
                    callbacks.onSecurityChanged(url.orEmpty().startsWith("https://"))
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    callbacks.onProgressChanged(100, isLoading = false)
                    view?.title?.let { callbacks.onTitleChanged(it) }
                    view?.let {
                        callbacks.onCanBackChanged(it.canGoBack())
                        callbacks.onCanForwardChanged(it.canGoForward())
                    }
                    // 同步 Cookie 到持久化(B 站登录态依赖)
                    runCatching { CookieManager.getInstance().flush() }
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean = false

                /** SSL 证书错误:默认 WebView 会取消加载,导致 https 站点完全打不开。
                 *  这里弹框让用户决定是否继续(国产 ROM 老 WebView 证书链校验常误报)。
                 *  用户选「继续」才 proceed,安全风险可控。 */
                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
                ) {
                    Log.w(TAG, "SSL error: ${error?.primaryError} url=${error?.url}")
                    try {
                        val ctx = view?.context ?: com.tvfoxbrowser.TvFoxApp.getApp()
                        AlertDialog.Builder(ctx)
                            .setTitle("SSL 证书错误")
                            .setMessage("该站点的 SSL 证书有问题(${error?.primaryError}),是否仍要继续访问?\n\nURL: ${error?.url}")
                            .setPositiveButton("继续") { _, _ -> handler?.proceed() }
                            .setNegativeButton("取消") { _, _ -> handler?.cancel() }
                            .setOnCancelListener { handler?.cancel() }
                            .show()
                    } catch (t: Throwable) {
                        // UI 上下文拿不到时,默认 proceed 避免站点完全打不开
                        Log.w(TAG, "Cannot show SSL dialog, proceed anyway", t)
                        handler?.proceed()
                    }
                }

                /** 网络层错误(DNS/连接失败等),打到 logcat 便于排查「连不上网」问题 */
                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    Log.w(TAG, "Resource error: ${error?.description} url=${request?.url}")
                    super.onReceivedError(view, request, error)
                }

                /** HTTP 错误(404/500 等) */
                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    Log.w(TAG, "HTTP ${errorResponse?.statusCode} url=${request?.url}")
                    super.onReceivedHttpError(view, request, errorResponse)
                }
            }
        } catch (t: Throwable) {
            Log.w(TAG, "setWebViewClient failed", t)
        }

        try {
            webView.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    callbacks.onProgressChanged(newProgress, isLoading = newProgress in 1..99)
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    callbacks.onTitleChanged(title.orEmpty())
                }

                // ===== H5 视频全屏(B 站/YouTube 点全屏必需)=====
                override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                    Log.d(TAG, "onShowCustomView: entering fullscreen")
                    // 先退出已有全屏(直接调用本 object 的清理逻辑,
                    // 不调用 onHideCustomView() 本身,避免 Kotlin 解析问题)
                    performHideCustomView()
                    currentCustomView = view
                    currentCustomViewCallback = callback
                    val container = fullscreenContainer
                    if (view != null && container != null) {
                        runCatching {
                            container.removeAllViews()
                            container.addView(
                                view,
                                FrameLayout.LayoutParams(
                                    FrameLayout.LayoutParams.MATCH_PARENT,
                                    FrameLayout.LayoutParams.MATCH_PARENT
                                )
                            )
                            container.visibility = View.VISIBLE
                        }
                    } else {
                        Log.w(TAG, "onShowCustomView: container or view is null, cannot display")
                    }
                }

                override fun onHideCustomView() {
                    Log.d(TAG, "onHideCustomView: leaving fullscreen")
                    performHideCustomView()
                }

                // ===== JS 弹框(缺这些会让某些站点卡死)=====
                override fun onJsAlert(
                    view: WebView?, url: String?, message: String?, result: android.webkit.JsResult?
                ): Boolean {
                    try {
                        AlertDialog.Builder(view?.context ?: com.tvfoxbrowser.TvFoxApp.getApp())
                            .setTitle("提示")
                            .setMessage(message ?: "")
                            .setPositiveButton("确定") { _, _ -> result?.confirm() }
                            .setOnCancelListener { result?.cancel() }
                            .show()
                    } catch (t: Throwable) {
                        result?.confirm()
                    }
                    return true
                }

                override fun onJsConfirm(
                    view: WebView?, url: String?, message: String?, result: android.webkit.JsResult?
                ): Boolean {
                    try {
                        AlertDialog.Builder(view?.context ?: com.tvfoxbrowser.TvFoxApp.getApp())
                            .setTitle("确认")
                            .setMessage(message ?: "")
                            .setPositiveButton("确定") { _, _ -> result?.confirm() }
                            .setNegativeButton("取消") { _, _ -> result?.cancel() }
                            .setOnCancelListener { result?.cancel() }
                            .show()
                    } catch (t: Throwable) {
                        result?.cancel()
                    }
                    return true
                }

                override fun onJsPrompt(
                    view: WebView?, url: String?, message: String?, defaultValue: String?,
                    result: android.webkit.JsPromptResult?
                ): Boolean {
                    try {
                        val edit = android.widget.EditText(view?.context ?: com.tvfoxbrowser.TvFoxApp.getApp())
                        edit.setText(defaultValue ?: "")
                        AlertDialog.Builder(view?.context ?: com.tvfoxbrowser.TvFoxApp.getApp())
                            .setTitle(message ?: "输入")
                            .setView(edit)
                            .setPositiveButton("确定") { _, _ -> result?.confirm(edit.text.toString()) }
                            .setNegativeButton("取消") { _, _ -> result?.cancel() }
                            .setOnCancelListener { result?.cancel() }
                            .show()
                    } catch (t: Throwable) {
                        result?.cancel()
                    }
                    return true
                }

                /** 控制台日志转发到 logcat,方便排查页面 JS 报错 */
                override fun onConsoleMessage(consoleMessage: android.webkit.ConsoleMessage?): Boolean {
                    Log.d("WebViewConsole", "[${consoleMessage?.messageLevel()}] ${consoleMessage?.message()} @ ${consoleMessage?.sourceId()}:${consoleMessage?.lineNumber()}")
                    return true
                }
            }
        } catch (t: Throwable) {
            Log.w(TAG, "setWebChromeClient failed", t)
        }

        return webView
    }

    /** 应用 UA(切换 UA 模式后对所有 WebView 重新调用) */
    fun applyUa(webView: WebView) {
        val ua = SettingsManager.get().uaMode
        runCatching {
            webView.settings.userAgentString = when (ua) {
                SettingsManager.UA_TV -> TV_UA
                SettingsManager.UA_DESKTOP -> DESKTOP_UA
                else -> null // mobile 用系统默认 UA
            }
        }
    }

    /** UA 模式切换后,对所有现存 WebView 重新应用 */
    fun reapplyUaForAll(webViews: List<WebView>) {
        webViews.forEach { runCatching { applyUa(it) } }
    }

    /** 退出当前全屏(供 BrowserFragment 在 BACK 键时调用) */
    fun exitFullscreenIfAny(): Boolean {
        if (currentCustomView != null) {
            performHideCustomView()
            return true
        }
        return false
    }

    /**
     * 实际执行退出全屏的清理逻辑。
     * 抽成独立函数,以便 WebChromeClient.onShowCustomView 和外部 BACK 键
     * 都能调用,而不必互相依赖 override 方法本身(Kotlin 在匿名 object 里
     * 调用同 object 的 override 方法偶尔会 Unresolved reference)。
     */
    private fun performHideCustomView() {
        val container = fullscreenContainer
        currentCustomView?.let { cv ->
            runCatching {
                (cv.parent as? ViewGroup)?.removeView(cv)
            }
        }
        container?.let {
            runCatching {
                it.visibility = View.GONE
                it.removeAllViews()
            }
        }
        currentCustomViewCallback?.let {
            runCatching { it.onCustomViewHidden() }
        }
        currentCustomView = null
        currentCustomViewCallback = null
    }

    // -------- 清理操作(供 SettingsFragment 调用) --------

    fun clearCache() {
        runCatching { WebStorage.getInstance().deleteAllData() }
    }

    fun clearCookies() {
        runCatching {
            CookieManager.getInstance().removeAllCookies(null)
            CookieManager.getInstance().flush()
        }
    }

    fun clearAllBrowsingData() {
        runCatching { WebStorage.getInstance().deleteAllData() }
        runCatching {
            CookieManager.getInstance().removeAllCookies(null)
            CookieManager.getInstance().flush()
        }
        // 清理 WebView 历史记录(老 API 才有 clearFormData,API 26+ 弃用但保留)
        runCatching {
            android.webkit.WebViewDatabase.getInstance(com.tvfoxbrowser.TvFoxApp.getApp())
                .clearFormData()
        }
    }
}

/** 单个 WebView 的事件回调(接口保持与原 GeckoEngine 一致,TabManager 无需改动) */
interface SessionCallbacks {
    fun onUrlChanged(url: String)
    fun onTitleChanged(title: String)
    fun onProgressChanged(progress: Int, isLoading: Boolean)
    fun onSecurityChanged(secure: Boolean)
    fun onCanBackChanged(canBack: Boolean)
    fun onCanForwardChanged(canForward: Boolean)
}

/** 聚合后的回调目标(由 TabManager 实现),携带 tabId 区分来源 */
interface AggregatedTarget {
    fun onUrlChanged(tabId: Long, url: String)
    fun onTitleChanged(tabId: Long, title: String)
    fun onProgressChanged(tabId: Long, progress: Int, isLoading: Boolean)
    fun onSecurityChanged(tabId: Long, secure: Boolean)
    fun onCanBackChanged(tabId: Long, canBack: Boolean)
    fun onCanForwardChanged(tabId: Long, canForward: Boolean)
}

/** 把单个 WebView 的回调按 tabId 转发给 TabManager */
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
