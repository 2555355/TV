package com.tvfoxbrowser.browser

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.widget.FrameLayout
import android.widget.Toast
import com.tvfoxbrowser.SettingsManager
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.video.VideoUrlInterceptor
import org.xwalk.core.CustomViewCallback
import org.xwalk.core.XWalkJavascriptResult
import org.xwalk.core.XWalkResourceClient
import org.xwalk.core.XWalkSettings
import org.xwalk.core.XWalkUIClient
import org.xwalk.core.XWalkView
import org.xwalk.core.XWalkWebResourceRequest
import org.xwalk.core.XWalkWebResourceResponse

/**
 * Crosswalk 嵌入式浏览器内核封装(替代系统 WebView)。
 *
 * 为什么换 Crosswalk:
 * 海尔 HRA920L (Android 5.1) 的系统 WebView 内核是 Chrome 39(2014 年),
 * 不支持 MSE(Media Source Extensions),B 站等现代视频站的 H5 播放器无法工作。
 * Crosswalk 23.53.589.4 自带 Chromium 53 内核(2016 年),完整支持 MSE/H.264/HLS,
 * 让 B 站视频可以在网页内直接播放,不再依赖外部播放器拦截。
 *
 * API 对应关系:
 * - android.webkit.WebView       -> org.xwalk.core.XWalkView
 * - android.webkit.WebViewClient -> org.xwalk.core.XWalkResourceClient
 * - android.webkit.WebChromeClient -> org.xwalk.core.XWalkUIClient
 * - android.webkit.WebSettings   -> org.xwalk.core.XWalkSettings
 *
 * 关键差异:
 * - XWalkView 构造需要 Activity(不能只用 Context)
 * - 全屏视频回调在 XWalkUIClient.onShowCustomView
 * - 网络拦截通过 XWalkResourceClient.shouldInterceptLoadRequest
 *
 * 海尔 HRA920L (Android 5.1, API 22) 兼容性:
 * - 所有 XWalkSettings 调用都用 try-catch 包裹
 * - createXWalkView 失败时返回 null,调用方决定降级
 */
object WebViewEngine {

    private const val TAG = "XWalkEngine"

    /** 电视端 UA:让站点识别为 Android TV */
    private const val TV_UA =
        "Mozilla/5.0 (Linux; Android 11; SmartTV) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36"

    /** 桌面端 UA(默认):用 Windows + Chrome 53,确保站点返回桌面版而非手机版
     *  注意:Crosswalk 内核就是 Chromium 53,UA 必须匹配否则部分站点会异常 */
    private const val DESKTOP_UA =
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36"

    /** 当前全屏视频宿主容器(由 BrowserFragment 设置,用于 XWalkUIClient.onShowCustomView) */
    @Volatile
    var fullscreenContainer: FrameLayout? = null

    /** 当前正在全屏显示的 View(退出全屏时需要从容器移除) */
    private var currentCustomView: View? = null
    private var currentCustomViewCallback: CustomViewCallback? = null

    /**
     * 视频地址拦截回调(由 BrowserFragment 设置)。
     * Crosswalk 自带 MSE 已能直接播放 B 站视频,此拦截器主要用于:
     * - m3u8 流媒体(Crosswalk 不一定支持所有 HLS)
     * - flv 等老格式
     * - 仍允许用户切换到 MX Player 硬解
     */
    @Volatile
    var onVideoFound: ((url: String, title: String?) -> Unit)? = null

    /** 每个 XWalkView 对应的拦截器(JS 注入 + 网络层兜底) */
    private val interceptors = mutableMapOf<XWalkView, VideoUrlInterceptor>()

    /** 主线程 Handler(把视频拦截回调切到主线程启动外部播放器) */
    private val mainHandler = android.os.Handler(android.os.Looper.getMainLooper())

    /** 持有当前 Activity 引用(XWalkView 构造需要) */
    @Volatile
    var currentActivity: Activity? = null

    /**
     * 创建一个配置好的 XWalkView 并绑定事件回调。
     * 必须在 XWalkInitializer 初始化完成后调用(否则 XWalkView 无法工作)。
     *
     * @return 配置好的 XWalkView;若 Crosswalk 不可用或初始化失败,返回 null
     */
    @SuppressLint("SetJavaScriptEnabled")
    fun createWebView(callbacks: SessionCallbacks): XWalkView? {
        val activity = currentActivity ?: run {
            Log.e(TAG, "currentActivity is null, cannot create XWalkView")
            return null
        }

        val xWalkView = try {
            XWalkView(activity, activity)
        } catch (t: Throwable) {
            Log.e(TAG, "XWalkView instantiation failed", t)
            return null
        }

        // 视频拦截器(JS 注入 + 网络层兜底)
        val interceptor = VideoUrlInterceptor { url, title ->
            mainHandler.post {
                Log.i(TAG, "Video intercepted, dispatching to launcher: $url")
                onVideoFound?.invoke(url, title)
            }
        }
        synchronized(interceptors) { interceptors[xWalkView] = interceptor }

        try {
            xWalkView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            xWalkView.isFocusable = false
            xWalkView.isFocusableInTouchMode = false
            // 硬件加速层(视频/动画必须)
            runCatching { xWalkView.setLayerType(View.LAYER_TYPE_HARDWARE, null) }
        } catch (t: Throwable) {
            Log.w(TAG, "XWalkView base setup failed", t)
        }

        // XWalkSettings(每个 setter 单独 try-catch,缺某些 API 时不致命)
        try {
            with(xWalkView.settings) {
                runCatching { javaScriptEnabled = SettingsManager.get().jsEnabled }
                runCatching { domStorageEnabled = true }
                runCatching { databaseEnabled = true }
                runCatching { useWideViewPort = true }
                runCatching { loadWithOverviewMode = true }
                runCatching { builtInZoomControls = false }
                runCatching { setSupportZoom(false) }
                runCatching { mediaPlaybackRequiresUserGesture = false }
                runCatching { javaScriptCanOpenWindowsAutomatically = true }
                runCatching { setSupportMultipleWindows(false) }
                runCatching { loadsImagesAutomatically = true }
                // Crosswalk 特有:允许混合内容
                runCatching {
                    // XWalkSettings 没有 mixedContentMode,但默认允许混合内容
                }
                // 缓存模式
                runCatching { cacheMode = XWalkSettings.LOAD_DEFAULT }
                // 允许文件/内容访问
                runCatching { allowFileAccess = true }
                runCatching { allowContentAccess = true }
                // Crosswalk 特有:启用 Flexbox 等现代 CSS(Chromium 53 已默认支持)
            }
        } catch (t: Throwable) {
            Log.w(TAG, "XWalkSettings bulk setup failed", t)
        }

        // 远程调试(同网段电脑 chrome://inspect 可调试)
        // Crosswalk 没有 setWebContentsDebuggingEnabled 静态方法,用实例方法 enableRemoteDebugging
        runCatching { xWalkView.enableRemoteDebugging() }

        runCatching { applyUa(xWalkView) }

        // XWalkResourceClient(类似 WebViewClient)
        try {
            xWalkView.setResourceClient(object : XWalkResourceClient(xWalkView) {
                override fun onLoadStarted(view: XWalkView?, url: String?) {
                    callbacks.onProgressChanged(0, isLoading = true)
                    callbacks.onUrlChanged(url.orEmpty())
                    callbacks.onSecurityChanged(url.orEmpty().startsWith("https://"))
                }

                override fun onProgressChanged(view: XWalkView?, progressInPercent: Int) {
                    callbacks.onProgressChanged(progressInPercent, isLoading = progressInPercent in 1..99)
                }

                override fun onLoadFinished(view: XWalkView?, url: String?) {
                    callbacks.onProgressChanged(100, isLoading = false)
                    view?.let {
                        val hist = it.navigationHistory
                        callbacks.onCanBackChanged(hist.canGoBack())
                        callbacks.onCanForwardChanged(hist.canGoForward())
                    }
                    // 注入视频拦截 JS(XWalkView 版本)
                    view?.let { v ->
                        synchronized(interceptors) { interceptors[v] }?.injectJs(v)
                    }
                }

                override fun onReceivedLoadError(
                    view: XWalkView?, errorCode: Int, description: String?, failingUrl: String?
                ) {
                    Log.w(TAG, "Load error: $errorCode $description url=$failingUrl")
                }

                override fun onReceivedSslError(
                    view: XWalkView?, callback: ValueCallback<Boolean>?, error: SslError?
                ) {
                    Log.w(TAG, "SSL error: ${error?.primaryError} url=${error?.url}")
                    try {
                        AlertDialog.Builder(view?.context ?: TvFoxApp.getApp())
                            .setTitle("SSL 证书错误")
                            .setMessage("该站点的 SSL 证书有问题(${error?.primaryError}),是否仍要继续访问?\n\nURL: ${error?.url}")
                            .setPositiveButton("继续") { _, _ -> callback?.onReceiveValue(true) }
                            .setNegativeButton("取消") { _, _ -> callback?.onReceiveValue(false) }
                            .setOnCancelListener { callback?.onReceiveValue(false) }
                            .show()
                    } catch (t: Throwable) {
                        Log.w(TAG, "Cannot show SSL dialog, proceed anyway", t)
                        callback?.onReceiveValue(true)
                    }
                }

                /** 网络层兜底拦截视频 URL */
                override fun shouldInterceptLoadRequest(
                    view: XWalkView?, request: XWalkWebResourceRequest?
                ): XWalkWebResourceResponse? {
                    if (view != null && request != null) {
                        try {
                            val url = request.url?.toString()
                            if (url != null) {
                                synchronized(interceptors) { interceptors[view] }
                                    ?.let { interceptor ->
                                        // VideoUrlInterceptor.maybeInterceptRequest 接受 android.webkit.WebResourceRequest,
                                        // 这里 XWalkWebResourceRequest 是不同类,直接判断 url 后回调
                                        if (isVideoUrl(url)) {
                                            interceptor.reportVideoUrl(url)
                                        }
                                    }
                            }
                        } catch (_: Throwable) {}
                    }
                    return null  // 不真正拦截,只观察
                }
            }
        } catch (t: Throwable) {
            Log.w(TAG, "setResourceClient failed", t)
        }

        // XWalkUIClient(类似 WebChromeClient)
        try {
            xWalkView.setUIClient(object : XWalkUIClient(xWalkView) {
                override fun onReceivedTitle(view: XWalkView?, title: String?) {
                    callbacks.onTitleChanged(title.orEmpty())
                }

                // ===== H5 视频全屏(B 站点全屏必需)=====
                override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                    Log.d(TAG, "onShowCustomView: entering fullscreen")
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
                        Log.w(TAG, "onShowCustomView: container or view is null")
                    }
                }

                override fun onHideCustomView() {
                    Log.d(TAG, "onHideCustomView: leaving fullscreen")
                    performHideCustomView()
                }

                // ===== JS 弹框 =====
                override fun onJsAlert(
                    view: XWalkView?, url: String?, message: String?, result: XWalkJavascriptResult?
                ): Boolean {
                    try {
                        AlertDialog.Builder(view?.context ?: TvFoxApp.getApp())
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
                    view: XWalkView?, url: String?, message: String?, result: XWalkJavascriptResult?
                ): Boolean {
                    try {
                        AlertDialog.Builder(view?.context ?: TvFoxApp.getApp())
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
                    view: XWalkView?, url: String?, message: String?, defaultValue: String?,
                    result: XWalkJavascriptResult?
                ): Boolean {
                    try {
                        val edit = android.widget.EditText(view?.context ?: TvFoxApp.getApp())
                        edit.setText(defaultValue ?: "")
                        AlertDialog.Builder(view?.context ?: TvFoxApp.getApp())
                            .setTitle(message ?: "输入")
                            .setView(edit)
                            .setPositiveButton("确定") { _, _ -> result?.confirmWithResult(edit.text.toString()) }
                            .setNegativeButton("取消") { _, _ -> result?.cancel() }
                            .setOnCancelListener { result?.cancel() }
                            .show()
                    } catch (t: Throwable) {
                        result?.cancel()
                    }
                    return true
                }

                /** 控制台日志转发到 logcat */
                override fun onConsoleMessage(
                    view: XWalkView?, message: String?, lineNumber: Int,
                    sourceId: String?, messageType: XWalkUIClient.ConsoleMessageType?
                ): Boolean {
                    Log.d("XWalkConsole", "[$messageType] $message @ $sourceId:$lineNumber")
                    return true
                }
            }
        } catch (t: Throwable) {
            Log.w(TAG, "setUIClient failed", t)
        }

        return xWalkView
    }

    /** 应用 UA(切换 UA 模式后对所有 XWalkView 重新调用) */
    fun applyUa(xWalkView: XWalkView) {
        val ua = SettingsManager.get().uaMode
        runCatching {
            xWalkView.settings.userAgentString = when (ua) {
                SettingsManager.UA_TV -> TV_UA
                SettingsManager.UA_DESKTOP -> DESKTOP_UA
                else -> null // mobile 用 Crosswalk 默认 UA
            }
        }
    }

    /** UA 模式切换后,对所有现存 XWalkView 重新应用 */
    fun reapplyUaForAll(xWalkViews: List<XWalkView>) {
        xWalkViews.forEach { runCatching { applyUa(it) } }
    }

    /** 退出当前全屏(供 BrowserFragment 在 BACK 键时调用) */
    fun exitFullscreenIfAny(): Boolean {
        if (currentCustomView != null) {
            performHideCustomView()
            return true
        }
        return false
    }

    private fun performHideCustomView() {
        val container = fullscreenContainer
        currentCustomView?.let { cv ->
            runCatching { (cv.parent as? ViewGroup)?.removeView(cv) }
        }
        container?.let {
            runCatching {
                it.visibility = View.GONE
                it.removeAllViews()
            }
        }
        currentCustomViewCallback?.let { cb ->
            runCatching { cb.onCustomViewHidden() }
        }
        currentCustomView = null
        currentCustomViewCallback = null
    }

    // -------- 清理操作 --------

    fun clearCache() {
        // Crosswalk 自带缓存管理
        runCatching {
            org.xwalk.core.XWalkPreferences.setValue(
                "enable-encoding-detection", true
            )
        }
        // 实际清理需要每个 XWalkView 调用 clearCache
    }

    fun clearCookies() {
        runCatching {
            android.webkit.CookieManager.getInstance().removeAllCookies(null)
            android.webkit.CookieManager.getInstance().flush()
        }
    }

    fun clearAllBrowsingData() {
        runCatching {
            android.webkit.CookieManager.getInstance().removeAllCookies(null)
            android.webkit.CookieManager.getInstance().flush()
        }
        runCatching {
            android.webkit.WebStorage.getInstance().deleteAllData()
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

/** 单个 XWalkView 的事件回调(接口保持与原 WebViewEngine 一致,TabManager 无需改动) */
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
