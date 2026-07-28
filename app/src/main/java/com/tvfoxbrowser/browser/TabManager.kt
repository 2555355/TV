package com.tvfoxbrowser.browser

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import java.util.concurrent.atomic.AtomicLong

/**
 * 标签页管理器。持有多个 Tab(每个对应一个 WebView),
 * 同一时刻只有一个 Tab 的 WebView 附加到容器 ViewGroup。
 *
 * 与 GeckoView 版本的差异:
 * - GeckoView 用 setSession 切换标签页(单一 GeckoView 持有当前 session)
 * - WebView 版本直接 addView/removeView 切换 WebView(每个 Tab 独立实例)
 *
 * 通过 [listener] 把 UI 相关变更回调给 BrowserFragment。
 */
class TabManager(
    private val container: ViewGroup
) : AggregatedTarget {

    private val tabs = mutableListOf<Tab>()
    private val idGen = AtomicLong(1)
    private var activeIndex: Int = -1

    var listener: TabListener? = null

    val tabCount: Int get() = tabs.size
    val allTabs: List<Tab> get() = tabs.toList()
    val activeTab: Tab? get() = tabs.getOrNull(activeIndex)

    interface TabListener {
        fun onActiveTabChanged(tab: Tab)
        fun onTabListChanged()
        fun onActiveTabUpdated(tab: Tab, field: TabField)
    }

    enum class TabField { URL, TITLE, PROGRESS, LOADING, SECURITY, NAV }

    /** 新建标签页并加载 URL;返回新 Tab */
    fun addTab(url: String = "about:blank"): Tab {
        val tabId = idGen.getAndIncrement()
        val callbacks = SessionCallbackAggregator(tabId, this)
        val webView = WebViewEngine.createWebView(callbacks)
        val tab = Tab(id = tabId, webView = webView, url = url)
        tabs.add(tab)
        setActive(tabs.size - 1)
        // about:home 是 UI 层虚拟 URL(由 HomeFragment 显示),
        // about:blank 是 WebView 内置空页,都不需要显式 loadUrl。
        if (webView != null && url != "about:blank" && !url.startsWith("about:home")) {
            runCatching { webView.loadUrl(url) }
                .onFailure { Log.e(TAG, "loadUrl failed", it) }
        }
        listener?.onTabListChanged()
        return tab
    }

    /** 切换到指定索引的标签页 */
    fun setActive(index: Int) {
        if (index !in tabs.indices) return
        // 已经是当前活动标签且 WebView 已在容器中,直接返回
        val target = tabs[index]
        val wv = target.webView
        if (index == activeIndex && wv?.parent == container) return

        // 移除当前容器里的所有 WebView(只有一个),加入目标的
        detachAllWebViews()
        if (wv != null) {
            runCatching { container.addView(wv) }
                .onFailure { Log.e(TAG, "addView failed", it) }
        }
        activeIndex = index

        listener?.onActiveTabChanged(target)
        listener?.onActiveTabUpdated(target, TabField.URL)
        listener?.onActiveTabUpdated(target, TabField.TITLE)
        listener?.onActiveTabUpdated(target, TabField.PROGRESS)
        listener?.onActiveTabUpdated(target, TabField.NAV)
    }

    fun setActiveById(id: Long) {
        val idx = tabs.indexOfFirst { it.id == id }
        if (idx >= 0) setActive(idx)
    }

    /** 关闭指定标签页 */
    fun closeTab(id: Long) {
        val idx = tabs.indexOfFirst { it.id == id }
        if (idx < 0) return
        val closing = tabs.removeAt(idx)
        closing.webView?.let { wv ->
            runCatching {
                (wv.parent as? ViewGroup)?.removeView(wv)
                wv.destroy()
            }
        }

        if (tabs.isEmpty()) {
            activeIndex = -1
            listener?.onTabListChanged()
            listener?.onActiveTabChanged(addTab("about:home"))
            return
        }
        // 调整活动索引
        if (idx <= activeIndex) {
            activeIndex = (activeIndex - 1).coerceAtLeast(0)
        }
        setActive(activeIndex)
        listener?.onTabListChanged()
    }

    /** 关闭全部,保留一个空白页 */
    fun closeAll() {
        tabs.forEach { tab ->
            tab.webView?.let { wv ->
                runCatching {
                    (wv.parent as? ViewGroup)?.removeView(wv)
                    wv.destroy()
                }
            }
        }
        tabs.clear()
        activeIndex = -1
        listener?.onTabListChanged()
        addTab("about:home")
    }

    fun goBack() {
        val wv = activeTab?.webView ?: return
        runCatching { if (wv.canGoBack()) wv.goBack() }
    }

    fun goForward() {
        val wv = activeTab?.webView ?: return
        runCatching { if (wv.canGoForward()) wv.goForward() }
    }

    fun reload() {
        activeTab?.webView?.let { runCatching { it.reload() } }
    }

    fun stop() {
        activeTab?.webView?.let { runCatching { it.stopLoading() } }
    }

    fun loadUrl(url: String) {
        val tab = activeTab ?: addTab(url)
        tab.url = url
        // about:home 不传给 WebView(同 addTab 的处理)
        if (!url.startsWith("about:home")) {
            tab.webView?.let { wv ->
                runCatching { wv.loadUrl(url) }
                    .onFailure { Log.e(TAG, "loadUrl failed", it) }
            }
        }
        listener?.onActiveTabUpdated(tab, TabField.URL)
    }

    // -------- SessionCallbacks 聚合:更新对应 Tab 模型 --------
    override fun onUrlChanged(tabId: Long, url: String) {
        val tab = tabs.firstOrNull { it.id == tabId } ?: return
        tab.url = url
        // WebView 的 canGoBack/canGoForward 在 URL 变化时也要更新
        val wv = tab.webView
        if (wv != null) {
            tab.canGoBack = runCatching { wv.canGoBack() }.getOrDefault(false)
            tab.canGoForward = runCatching { wv.canGoForward() }.getOrDefault(false)
        }
        if (tab.id == activeTab?.id) {
            listener?.onActiveTabUpdated(tab, TabField.URL)
            listener?.onActiveTabUpdated(tab, TabField.NAV)
        }
    }

    override fun onTitleChanged(tabId: Long, title: String) {
        val tab = tabs.firstOrNull { it.id == tabId } ?: return
        tab.title = title
        if (tab.id == activeTab?.id) {
            listener?.onActiveTabUpdated(tab, TabField.TITLE)
        } else {
            // 后台标签页标题更新,通知列表刷新缩略图标题
            listener?.onTabListChanged()
        }
    }

    override fun onProgressChanged(tabId: Long, progress: Int, isLoading: Boolean) {
        val tab = tabs.firstOrNull { it.id == tabId } ?: return
        tab.progress = progress
        tab.isLoading = isLoading
        if (tab.id == activeTab?.id) {
            listener?.onActiveTabUpdated(tab, TabField.PROGRESS)
        }
    }

    override fun onSecurityChanged(tabId: Long, secure: Boolean) {
        val tab = tabs.firstOrNull { it.id == tabId } ?: return
        tab.isSecure = secure
        if (tab.id == activeTab?.id) {
            listener?.onActiveTabUpdated(tab, TabField.SECURITY)
        }
    }

    override fun onCanBackChanged(tabId: Long, canBack: Boolean) {
        val tab = tabs.firstOrNull { it.id == tabId } ?: return
        tab.canGoBack = canBack
        if (tab.id == activeTab?.id) {
            listener?.onActiveTabUpdated(tab, TabField.NAV)
        }
    }

    override fun onCanForwardChanged(tabId: Long, canForward: Boolean) {
        val tab = tabs.firstOrNull { it.id == tabId } ?: return
        tab.canGoForward = canForward
        if (tab.id == activeTab?.id) {
            listener?.onActiveTabUpdated(tab, TabField.NAV)
        }
    }

    fun destroy() {
        tabs.forEach { tab ->
            tab.webView?.let { wv ->
                runCatching {
                    (wv.parent as? ViewGroup)?.removeView(wv)
                    wv.destroy()
                }
            }
        }
        tabs.clear()
        activeIndex = -1
    }

    /** 把容器里所有 WebView 移除(但不 destroy,保留状态用于后台标签) */
    private fun detachAllWebViews() {
        for (i in 0 until container.childCount) {
            val child = container.getChildAt(i)
            if (child is WebView) {
                container.removeView(child)
                // removeView 会改变 childCount,移除后需要重新从 0 开始
                break
            }
        }
    }

    companion object {
        private const val TAG = "TabManager"
    }
}
