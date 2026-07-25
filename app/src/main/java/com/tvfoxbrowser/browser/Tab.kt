package com.tvfoxbrowser.browser

import org.mozilla.geckoview.GeckoSession

/**
 * 单个标签页模型。每个标签页对应一个 GeckoSession。
 * 后台标签页保持 session 打开,但不附加到 GeckoView。
 */
data class Tab(
    val id: Long,
    val session: GeckoSession,
    var url: String = "about:blank",
    var title: String = "",
    var progress: Int = 100,
    var isLoading: Boolean = false,
    var canGoBack: Boolean = false,
    var canGoForward: Boolean = false,
    var isSecure: Boolean = false
) {
    val displayTitle: String
        get() = if (title.isBlank()) url.takeIf { it.isNotBlank() && it != "about:blank" } ?: "新标签页" else title
}
