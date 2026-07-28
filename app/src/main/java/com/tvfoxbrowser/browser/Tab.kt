package com.tvfoxbrowser.browser

import android.webkit.WebView

/**
 * 单个标签页模型。每个标签页对应一个 WebView 实例。
 * 后台标签页保持 WebView 实例存在,但不附加到容器。
 *
 * [webView] 可为 null:当系统 WebView 不可用(被 ROM 阉割)时,
 * TabManager 仍会创建 Tab 占位,让 UI 至少能显示错误页而不是闪退。
 */
data class Tab(
    val id: Long,
    val webView: WebView?,
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
