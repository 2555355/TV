package com.tvfoxbrowser.browser

import com.tvfoxbrowser.SettingsManager
import com.tvfoxbrowser.TvFoxApp
import org.mozilla.geckoview.AllowOrDeny
import org.mozilla.geckoview.GeckoResult
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoSessionSettings
import org.mozilla.geckoview.StorageController

/**
 * GeckoView 内核封装。
 * - 根据 UA 模式构造 GeckoSessionSettings
 * - 创建带完整 Delegate 链的 GeckoSession (Firefox 引擎)
 * - 应用 UA 覆盖 (电视端专用 UA)
 *
 * GeckoRuntime 由 TvFoxApp 持有,进程内单例。
 *
 * 注意:NavigationDelegate.onLocationChange 在 GeckoView 124 为 3 参签名
 * (session, url, perms)。若升级到 GeckoView 125+,需追加第 4 个参数
 * hasUserGesture: Boolean(参见 bug 1804636)。
 */
object GeckoEngine {

    /** 电视端 UA:让站点识别为 Android TV,优先返回 TV 适配页面 */
    private const val TV_UA =
        "Mozilla/5.0 (Linux; Android 11; SmartTV) Gecko/124.0 Firefox/124.0"

    private const val DESKTOP_UA =
        "Mozilla/5.0 (X11; Linux x86_64; rv:124.0) Gecko/20100101 Firefox/124.0"

    private fun buildSettings(): GeckoSessionSettings {
        val ua = SettingsManager.get().uaMode
        val mode = when (ua) {
            SettingsManager.UA_DESKTOP -> GeckoSessionSettings.USER_AGENT_MODE_DESKTOP
            else -> GeckoSessionSettings.USER_AGENT_MODE_MOBILE // tv 走 mobile 模式 + override
        }
        return GeckoSessionSettings.Builder()
            .userAgentMode(mode)
            .viewportMode(GeckoSessionSettings.VIEWPORT_MODE_MOBILE)
            .usePrivateMode(false)
            .useTrackingProtection(SettingsManager.get().doNotTrack)
            .build()
    }

    fun applyUaOverride(session: GeckoSession) {
        val ua = SettingsManager.get().uaMode
        session.settings.userAgentOverride = when (ua) {
            SettingsManager.UA_TV -> TV_UA
            SettingsManager.UA_DESKTOP -> DESKTOP_UA
            else -> null // mobile 用 GeckoView 默认 UA
        }
    }

    fun createSession(callbacks: SessionCallbacks): GeckoSession {
        val session = GeckoSession(buildSettings())
        applyUaOverride(session)
        session.attachHelper(callbacks)
        // session.open 可能在 GPU 驱动异常时抛 Java 异常(如 IllegalStateException),
        // 用 try-catch 包裹,让上层有机会显示错误页而非直接 crash。
        // 注意:native SIGSEGV 仍抓不到,但 Java 异常可以。
        session.open(TvFoxApp.getRuntime())
        return session
    }

    /** 安全创建 session,失败返回 null 并记录错误 */
    fun createSessionSafely(callbacks: SessionCallbacks): GeckoSession? = runCatching {
        createSession(callbacks)
    }.onFailure { e ->
        android.util.Log.e("GeckoEngine", "createSession failed", e)
    }.getOrNull()

    /** UA 模式切换后,对所有现存 session 重新应用 */
    fun reapplyUaForAll(sessions: List<GeckoSession>) {
        sessions.forEach { applyUaOverride(it) }
    }

    fun clearCache() {
        val flags = (StorageController.ClearFlags.ALL_CACHES
            or StorageController.ClearFlags.IMAGE_CACHE)
        TvFoxApp.getRuntime().storageController.clearData(flags)
    }

    fun clearCookies() {
        val flags = (StorageController.ClearFlags.COOKIES
            or StorageController.ClearFlags.DOM_STORAGES
            or StorageController.ClearFlags.SITE_DATA
            or StorageController.ClearFlags.PERMISSIONS)
        TvFoxApp.getRuntime().storageController.clearData(flags)
    }

    fun clearAllBrowsingData() {
        val flags = (StorageController.ClearFlags.ALL_CACHES
            or StorageController.ClearFlags.COOKIES
            or StorageController.ClearFlags.DOM_STORAGES
            or StorageController.ClearFlags.IMAGE_CACHE
            or StorageController.ClearFlags.SITE_DATA
            or StorageController.ClearFlags.PERMISSIONS)
        TvFoxApp.getRuntime().storageController.clearData(flags)
    }
}

/** 单个 Session 的事件回调,由 Aggregator 实现并转发给 TabManager */
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

/** 把单个 session 的回调按 tabId 转发给 TabManager */
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
    override fun onCanForwardChanged(canForward: Boolean) = target.onCanForwardChanged(tabId, canForward)
}

private fun GeckoSession.attachHelper(cb: SessionCallbacks) {
    contentDelegate = object : GeckoSession.ContentDelegate {
        override fun onTitleChange(session: GeckoSession, title: String?) {
            cb.onTitleChanged(title.orEmpty())
        }
    }

    progressDelegate = object : GeckoSession.ProgressDelegate {
        override fun onPageStart(session: GeckoSession, url: String) {
            cb.onProgressChanged(0, isLoading = true)
            cb.onUrlChanged(url)
        }

        override fun onPageStop(session: GeckoSession, success: Boolean) {
            cb.onProgressChanged(100, isLoading = false)
        }

        override fun onProgressChange(session: GeckoSession, progress: Int) {
            cb.onProgressChanged(progress, isLoading = progress in 1..99)
        }
    }

    navigationDelegate = object : GeckoSession.NavigationDelegate {
        override fun onLocationChange(
            session: GeckoSession,
            url: String?,
            perms: MutableList<GeckoSession.PermissionDelegate.ContentPermission>
        ) {
            cb.onUrlChanged(url.orEmpty())
        }

        override fun onCanGoBack(session: GeckoSession, canGoBack: Boolean) {
            cb.onCanBackChanged(canGoBack)
        }

        override fun onCanGoForward(session: GeckoSession, canGoForward: Boolean) {
            cb.onCanForwardChanged(canGoForward)
        }

        override fun onLoadRequest(
            session: GeckoSession,
            request: GeckoSession.NavigationDelegate.LoadRequest
        ): GeckoResult<AllowOrDeny>? {
            return null // 默认允许
        }
    }
}
