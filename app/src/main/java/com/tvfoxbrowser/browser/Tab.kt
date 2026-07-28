package com.tvfoxbrowser.browser

import org.mozilla.geckoview.GeckoView

/**
 * 单个标签页模型。每个标签页对应一个 GeckoView + GeckoSession 实例。
 * 后台标签页保持 GeckoView 实例存在,但不附加到容器。
 *
 * [geckoView] 可为 null:当 GeckoRuntime 初始化失败时,
 * TabManager 仍会创建 Tab 占位,让 UI 至少能显示错误页而不是闪退。
 */
data class Tab(
    val id: Long,
    val geckoView: GeckoView?,
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
