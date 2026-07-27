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

    /** 新建标签页并加载 URL;返回新 Tab。session 创建失败时返回 null */
    fun addTab(url: String = "about:blank"): Tab? {
        val tabId = idGen.getAndIncrement()
        val callbacks = SessionCallbackAggregator(tabId, this)
        // 用安全版本:session 创建失败(GPU/驱动异常)时返回 null,
        // 让 UI 层显示错误页而非整个进程崩溃。
        val session = GeckoEngine.createSessionSafely(callbacks) ?: return null
        val tab = Tab(id = tabId, session = session, url = url)
        tabs.add(tab)
        setActive(tabs.size - 1)
        // about:home 是 UI 层虚拟 URL(由 HomeFragment 显示),
        // about:blank 是 GeckoView 内置空页,都不需要显式 loadUri。
        // 若把 about:home 传给 GeckoView,会让 session 进入异常状态,
        // 后续 loadUri 真实 URL 时可能触发 native 崩溃。
        if (url != "about:blank" && !url.startsWith("about:home")) {
            runCatching { session.loadUri(url) }
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
            addTab("about:home")?.let { listener?.onActiveTabChanged(it) }
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
        val tab = activeTab ?: addTab(url) ?: return
        tab.url = url
        // about:home 不传给 GeckoView(同 addTab 的处理)
        if (!url.startsWith("about:home")) {
            runCatching { tab.session.loadUri(url) }
        }
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
