package com.tvfoxbrowser.browser

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewClient
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
 */
object WebViewEngine {

    private const val TAG = "WebViewEngine"

    /** 电视端 UA:让站点识别为 Android TV */
    private const val TV_UA =
        "Mozilla/5.0 (Linux; Android 11; SmartTV) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"

    private const val DESKTOP_UA =
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"

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
        } catch (t: Throwable) {
            Log.w(TAG, "WebView base setup failed", t)
        }

        // WebSettings 每个属性都单独包 try-catch:
        // 老 WebView 内核缺少某些 API setter 时,只跳过该项,不致命。
        try {
            with(webView.settings) {
                runCatching { javaScriptEnabled = SettingsManager.get().jsEnabled }
                runCatching { domStorageEnabled = true }
                runCatching { databaseEnabled = true }
                runCatching { useWideViewPort = true }
                runCatching { loadWithOverviewMode = true }
                runCatching { cacheMode = WebSettings.LOAD_DEFAULT }
                runCatching { builtInZoomControls = false }
                runCatching { displayZoomControls = false }
                runCatching { setSupportZoom(false) }
                runCatching { mediaPlaybackRequiresUserGesture = false }
                // 允许混合内容(部分老站点 http 资源),API 21+
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    runCatching { mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE }
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
                    // 加载完成时刷新导航按钮状态
                    view?.let {
                        callbacks.onCanBackChanged(it.canGoBack())
                        callbacks.onCanForwardChanged(it.canGoForward())
                    }
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean = false
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
