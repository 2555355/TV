package com.tvfoxbrowser.browser

import android.util.Log
import android.view.View
import android.view.ViewGroup
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView
import java.util.concurrent.atomic.AtomicLong

/**
 * 标签页管理器。持有多个 Tab(每个对应一个 GeckoView),
 * 同一时刻只有一个 Tab 的 GeckoView 附加到容器 ViewGroup。
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
        val geckoView = WebViewEngine.createWebView(callbacks)
        val tab = Tab(id = tabId, geckoView = geckoView, url = url)
        tabs.add(tab)
        setActive(tabs.size - 1)
        // about:home 是 UI 层虚拟 URL(由 HomeFragment 显示),
        // about:blank 是 GeckoView 内置空页,都不需要显式 loadUri。
        if (geckoView != null && url != "about:blank" && !url.startsWith("about:home")) {
            runCatching { geckoView.session?.loadUri(url) }
                .onFailure { Log.e(TAG, "loadUri failed", it) }
        }
        listener?.onTabListChanged()
        return tab
    }

    /** 切换到指定索引的标签页 */
    fun setActive(index: Int) {
        if (index !in tabs.indices) return
        val target = tabs[index]
        val gv = target.geckoView
        if (index == activeIndex && gv?.parent == container) return

        detachAllGeckoViews()
        if (gv != null) {
            // 记录原始容器,供退出全屏时恢复用
            gv.tag = container
            runCatching { container.addView(gv) }
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
        closing.geckoView?.let { gv ->
            runCatching {
                (gv.parent as? ViewGroup)?.removeView(gv)
                gv.session?.close()
            }
        }

        if (tabs.isEmpty()) {
            activeIndex = -1
            listener?.onTabListChanged()
            listener?.onActiveTabChanged(addTab("about:home"))
            return
        }
        if (idx <= activeIndex) {
            activeIndex = (activeIndex - 1).coerceAtLeast(0)
        }
        setActive(activeIndex)
        listener?.onTabListChanged()
    }

    /** 关闭全部,保留一个空白页 */
    fun closeAll() {
        tabs.forEach { tab ->
            tab.geckoView?.let { gv ->
                runCatching {
                    (gv.parent as? ViewGroup)?.removeView(gv)
                    gv.session?.close()
                }
            }
        }
        tabs.clear()
        activeIndex = -1
        listener?.onTabListChanged()
        addTab("about:home")
    }

    fun goBack() {
        val session = activeTab?.geckoView?.session ?: return
        runCatching { session.goBack() }
    }

    fun goForward() {
        val session = activeTab?.geckoView?.session ?: return
        runCatching { session.goForward() }
    }

    fun reload() {
        activeTab?.geckoView?.session?.let { runCatching { it.reload() } }
    }

    fun stop() {
        activeTab?.geckoView?.session?.let { runCatching { it.stop() } }
    }

    fun loadUrl(url: String) {
        val tab = activeTab ?: addTab(url)
        tab.url = url
        if (!url.startsWith("about:home")) {
            tab.geckoView?.session?.let { s ->
                runCatching { s.loadUri(url) }
                    .onFailure { Log.e(TAG, "loadUri failed", it) }
            }
        }
        listener?.onActiveTabUpdated(tab, TabField.URL)
    }

    // -------- SessionCallbacks 聚合:更新对应 Tab 模型 --------
    override fun onUrlChanged(tabId: Long, url: String) {
        val tab = tabs.firstOrNull { it.id == tabId } ?: return
        tab.url = url
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
            tab.geckoView?.let { gv ->
                runCatching {
                    (gv.parent as? ViewGroup)?.removeView(gv)
                    gv.session?.close()
                }
            }
        }
        tabs.clear()
        activeIndex = -1
    }

    /** 把容器里所有 GeckoView 移除(但不 close,保留状态用于后台标签) */
    private fun detachAllGeckoViews() {
        for (i in 0 until container.childCount) {
            val child = container.getChildAt(i)
            if (child is GeckoView) {
                container.removeView(child)
                break
            }
        }
    }

    companion object {
        private const val TAG = "TabManager"
    }
}
