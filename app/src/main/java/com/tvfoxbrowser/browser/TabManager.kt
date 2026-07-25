package com.tvfoxbrowser.browser

import android.util.Log
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView
import java.util.concurrent.atomic.AtomicLong

/**
 * 标签页管理器。持有多个 Tab(每个对应一个 GeckoSession),
 * 同一时刻只有一个 Tab 附加到 GeckoView。
 *
 * 通过 [listener] 把 UI 相关变更回调给 BrowserFragment。
 */
class TabManager(
    private val geckoView: GeckoView
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
        val session = GeckoEngine.createSession(callbacks)
        val tab = Tab(id = tabId, session = session, url = url)
        tabs.add(tab)
        setActive(tabs.size - 1)
        if (url != "about:blank") {
            session.loadUri(url)
        }
        listener?.onTabListChanged()
        return tab
    }

    /** 切换到指定索引的标签页 */
    fun setActive(index: Int) {
        if (index !in tabs.indices) return
        if (index == activeIndex && geckoView.session == tabs[index].session) return
        activeIndex = index
        val tab = tabs[index]
        geckoView.setSession(tab.session)
        listener?.onActiveTabChanged(tab)
        listener?.onActiveTabUpdated(tab, TabField.URL)
        listener?.onActiveTabUpdated(tab, TabField.TITLE)
        listener?.onActiveTabUpdated(tab, TabField.PROGRESS)
        listener?.onActiveTabUpdated(tab, TabField.NAV)
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
        runCatching { closing.session.close() }

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
        tabs.forEach { runCatching { it.session.close() } }
        tabs.clear()
        activeIndex = -1
        listener?.onTabListChanged()
        addTab("about:home")
    }

    fun goBack() {
        activeTab?.session?.goBack()
    }

    fun goForward() {
        activeTab?.session?.goForward()
    }

    fun reload() {
        activeTab?.session?.reload()
    }

    fun stop() {
        activeTab?.session?.stop()
    }

    fun loadUrl(url: String) {
        val tab = activeTab ?: addTab(url)
        tab.url = url
        tab.session.loadUri(url)
        listener?.onActiveTabUpdated(tab, TabField.URL)
    }

    // -------- SessionCallbacks 聚合:更新对应 Tab 模型 --------
    override fun onUrlChanged(tabId: Long, url: String) {
        val tab = tabs.firstOrNull { it.id == tabId } ?: return
        tab.url = url
        if (tab.id == activeTab?.id) {
            listener?.onActiveTabUpdated(tab, TabField.URL)
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
        tabs.forEach { runCatching { it.session.close() } }
        tabs.clear()
        activeIndex = -1
    }

    companion object {
        private const val TAG = "TabManager"
    }
}
