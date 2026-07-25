package com.tvfoxbrowser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.tvfoxbrowser.BookmarkManager
import com.tvfoxbrowser.HistoryManager
import com.tvfoxbrowser.R
import com.tvfoxbrowser.SearchEngine
import com.tvfoxbrowser.browser.Tab
import com.tvfoxbrowser.browser.TabManager
import com.tvfoxbrowser.databinding.FragmentBrowserBinding
import com.tvfoxbrowser.home.HomeFragment

/**
 * 浏览器主控制器。
 * - 持有 GeckoView + TabManager
 * - 顶栏所有按钮的遥控器可聚焦交互
 * - 地址栏输入 -> 加载
 * - about:home 时显示主页门户
 */
class BrowserFragment :
    Fragment(),
    TabManager.TabListener,
    OverlayFragment.Host {

    private var _binding: FragmentBrowserBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabManager: TabManager
    private var homeFragment: HomeFragment? = null

    // 用于避免地址栏被回调覆盖时打断用户输入
    private var userEditingAddress = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrowserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabManager = TabManager(binding.geckoView).also { it.listener = this }

        setupTopBar()
        setupAddressBar()

        // 首个标签页加载主页
        if (savedInstanceState == null) {
            tabManager.addTab("about:home")
        }
    }

    private fun setupTopBar() {
        binding.topBar.btnBack.setOnClickListener { tabManager.goBack() }
        binding.topBar.btnForward.setOnClickListener { tabManager.goForward() }
        binding.topBar.btnReload.setOnClickListener { tabManager.reload() }
        binding.topBar.btnHome.setOnClickListener { goHome() }
        binding.topBar.btnBookmark.setOnClickListener { toggleBookmark() }
        binding.topBar.btnHistory.setOnClickListener { showOverlay(OverlayFragment.Mode.HISTORY) }
        binding.topBar.btnTabs.setOnClickListener { showOverlay(OverlayFragment.Mode.TABS) }
        binding.topBar.btnSettings.setOnClickListener { showSettings() }
    }

    private fun setupAddressBar() {
        val et = binding.topBar.addressBar
        et.setOnFocusChangeListener { _, hasFocus ->
            userEditingAddress = hasFocus
            if (hasFocus) et.selectAll()
        }
        et.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                val text = v.text.toString().trim()
                if (text.isNotEmpty()) {
                    val url = SearchEngine.normalize(text)
                    tabManager.loadUrl(url)
                    v.clearFocus()
                }
                true
            } else false
        }
    }

    private fun goHome() {
        val tab = tabManager.activeTab ?: tabManager.addTab("about:home")
        tab.url = "about:home"
        tabManager.loadUrl("about:home")
        showHome(true)
    }

    private fun toggleBookmark() {
        val tab = tabManager.activeTab ?: return
        val url = tab.url
        if (url.isBlank() || url.startsWith("about:")) return
        if (BookmarkManager.get().contains(url)) {
            BookmarkManager.get().remove(url)
        } else {
            BookmarkManager.get().add(url, tab.displayTitle)
        }
        homeFragment?.refreshPortals()
    }

    // -------- TabManager.TabListener --------
    override fun onActiveTabChanged(tab: Tab) {
        updateAddressBar(tab.url)
        updateNavButtons(tab)
        updateProgress(tab)
        val isHome = tab.url.startsWith("about:home") || tab.url == "about:blank"
        showHome(isHome)
        if (!isHome) HistoryManager.get().add(tab.url, tab.title)
    }

    override fun onTabListChanged() {
        // 由 Tabs Overlay 在打开时刷新
    }

    override fun onActiveTabUpdated(tab: Tab, field: TabManager.TabField) {
        when (field) {
            TabManager.TabField.URL -> {
                updateAddressBar(tab.url)
                val isHome = tab.url.startsWith("about:home")
                showHome(isHome)
                if (!isHome && tab.title.isNotBlank()) HistoryManager.get().add(tab.url, tab.title)
            }
            TabManager.TabField.TITLE -> {
                if (!tab.url.startsWith("about:") && tab.title.isNotBlank()) {
                    HistoryManager.get().add(tab.url, tab.title)
                }
            }
            TabManager.TabField.PROGRESS, TabManager.TabField.LOADING -> updateProgress(tab)
            TabManager.TabField.NAV -> updateNavButtons(tab)
            TabManager.TabField.SECURITY -> { /* 可扩展显示锁图标 */ }
        }
    }

    private fun updateAddressBar(url: String) {
        if (userEditingAddress) return
        val et = binding.topBar.addressBar
        val display = if (url.startsWith("about:")) "" else url
        if (et.text?.toString() != display) {
            et.setText(display)
        }
    }

    private fun updateNavButtons(tab: Tab) {
        binding.topBar.btnBack.isEnabled = tab.canGoBack
        binding.topBar.btnBack.alpha = if (tab.canGoBack) 1f else 0.35f
        binding.topBar.btnForward.isEnabled = tab.canGoForward
        binding.topBar.btnForward.alpha = if (tab.canGoForward) 1f else 0.35f
    }

    private fun updateProgress(tab: Tab) {
        binding.pageProgress.isVisible = tab.isLoading
        if (tab.isLoading) {
            binding.pageProgress.progress = tab.progress
            binding.pageProgress.visibility = View.VISIBLE
        } else {
            binding.pageProgress.visibility = View.GONE
        }
    }

    private fun showHome(show: Boolean) {
        binding.homeContainer.visibility = if (show) View.VISIBLE else View.GONE
        binding.geckoView.visibility = if (show) View.GONE else View.VISIBLE
        if (show) {
            if (homeFragment == null) {
                homeFragment = HomeFragment().also { hf ->
                    hf.onNavigate = { url ->
                        tabManager.loadUrl(url)
                        showHome(false)
                    }
                }
                childFragmentManager.beginTransaction()
                    .replace(R.id.home_container, homeFragment!!)
                    .commitNowAllowingStateLoss()
            }
            homeFragment?.refreshPortals()
        }
    }

    // -------- OverlayFragment.Host --------
    override fun tabManager(): TabManager = tabManager

    override fun onOpenUrl(url: String) {
        tabManager.loadUrl(url)
        showHome(url.startsWith("about:home"))
        closeOverlay()
    }

    override fun onOpenTab(tab: Tab) {
        tabManager.setActiveById(tab.id)
        closeOverlay()
    }

    override fun onCloseTab(tab: Tab) {
        tabManager.closeTab(tab.id)
        // 重建 Tabs overlay 以刷新列表
        showOverlay(OverlayFragment.Mode.TABS)
    }

    override fun onNewTab(url: String) {
        tabManager.addTab(url)
        showHome(url.startsWith("about:home"))
        closeOverlay()
    }

    override fun onCloseOverlay() = closeOverlay()

    // -------- MainActivity 调用 --------
    fun showOverlay(mode: OverlayFragment.Mode) {
        val frag = OverlayFragment.newInstance(mode)
        childFragmentManager.beginTransaction()
            .replace(R.id.overlay_container, frag, TAG_OVERLAY)
            .commitAllowingStateLoss()
    }

    fun showSettings() {
        val settings = com.tvfoxbrowser.settings.SettingsFragment()
        settings.bindTabManager(tabManager)
        settings.onClose = { closeOverlay() }
        childFragmentManager.beginTransaction()
            .replace(R.id.overlay_container, settings, TAG_SETTINGS)
            .commitAllowingStateLoss()
    }

    fun closeOverlay() {
        val tx = childFragmentManager.beginTransaction()
        childFragmentManager.findFragmentByTag(TAG_OVERLAY)?.let { tx.remove(it) }
        childFragmentManager.findFragmentByTag(TAG_SETTINGS)?.let { tx.remove(it) }
        tx.commitAllowingStateLoss()
    }

    fun onBackPressed(): Boolean {
        val overlay = childFragmentManager.findFragmentByTag(TAG_OVERLAY)
        val settings = childFragmentManager.findFragmentByTag(TAG_SETTINGS)
        if (overlay != null || settings != null) {
            closeOverlay()
            return true
        }
        val tab = tabManager.activeTab
        if (tab != null && tab.canGoBack) {
            tabManager.goBack()
            return true
        }
        if (tab != null && !tab.url.startsWith("about:home")) {
            goHome()
            return true
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabManager.destroy()
        _binding = null
    }

    companion object {
        private const val TAG_OVERLAY = "overlay"
        private const val TAG_SETTINGS = "settings"
    }
}
