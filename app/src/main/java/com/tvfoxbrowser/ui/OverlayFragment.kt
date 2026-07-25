package com.tvfoxbrowser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tvfoxbrowser.BookmarkManager
import com.tvfoxbrowser.HistoryManager
import com.tvfoxbrowser.R
import com.tvfoxbrowser.browser.Tab
import com.tvfoxbrowser.browser.TabManager
import com.tvfoxbrowser.databinding.OverlayListBinding

/**
 * 全屏 Overlay,承载三种视图:标签页 / 历史 / 书签。
 * 通过 [mode] 参数区分,由 BrowserFragment 在 overlay_container 中显示。
 */
class OverlayFragment : DialogFragment() {

    enum class Mode { TABS, HISTORY, BOOKMARKS }

    interface Host {
        fun tabManager(): TabManager
        fun onOpenUrl(url: String)
        fun onOpenTab(tab: Tab)
        fun onCloseTab(tab: Tab)
        fun onNewTab(url: String)
        fun onCloseOverlay()
    }

    private var _binding: OverlayListBinding? = null
    private val binding get() = _binding!!

    private val tabAdapter = TabAdapter()
    private val listAdapter = ListItemAdapter()
    private var host: Host? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_TvFoxBrowser)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OverlayListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        host = parentFragment as? Host ?: activity as? Host

        binding.btnCloseOverlay.setOnClickListener {
            host?.onCloseOverlay()
        }

        when (mode) {
            Mode.TABS -> setupTabs()
            Mode.HISTORY -> setupHistory()
            Mode.BOOKMARKS -> setupBookmarks()
        }
    }

    private fun setupTabs() {
        binding.overlayTitle.text = getString(R.string.tabs_title)
        binding.btnAddTab.visibility = View.VISIBLE
        binding.btnAddTab.setOnClickListener { host?.onNewTab("about:home") }

        val lm = GridLayoutManager(requireContext(), 4)
        binding.overlayList.layoutManager = lm
        binding.overlayList.adapter = tabAdapter
        binding.overlayList.addItemDecoration(
            GridSpacingItemDecoration(4, resources.getDimensionPixelSize(R.dimen.card_spacing), true)
        )

        tabAdapter.onSelect = { host?.onOpenTab(it) }
        tabAdapter.onClose = { host?.onCloseTab(it) }
        refreshTabs()
    }

    private fun refreshTabs() {
        val tm = host?.tabManager() ?: return
        val active = tm.activeTab
        tabAdapter.submit(tm.allTabs, active?.id ?: -1)
        binding.overlayEmpty.visibility = if (tm.tabCount == 0) View.VISIBLE else View.GONE
        binding.overlayEmpty.text = getString(R.string.tabs_title)
    }

    private fun setupHistory() {
        binding.overlayTitle.text = getString(R.string.history_title)
        binding.btnAddTab.visibility = View.GONE
        binding.overlayList.layoutManager = LinearLayoutManager(requireContext())
        binding.overlayList.adapter = listAdapter

        listAdapter.onClick = { item ->
            (item.tag as? String)?.let { host?.onOpenUrl(it) }
        }
        val items = HistoryManager.get().all().map {
            ListItemAdapter.Item(it.title, it.url, it.url)
        }
        listAdapter.submit(items)
        binding.overlayEmpty.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        binding.overlayEmpty.text = getString(R.string.history_empty)
    }

    private fun setupBookmarks() {
        binding.overlayTitle.text = getString(R.string.bookmarks_title)
        binding.btnAddTab.visibility = View.GONE
        binding.overlayList.layoutManager = LinearLayoutManager(requireContext())
        binding.overlayList.adapter = listAdapter

        listAdapter.onClick = { item ->
            (item.tag as? String)?.let { host?.onOpenUrl(it) }
        }
        val items = BookmarkManager.get().all().map {
            ListItemAdapter.Item(it.title, it.url, it.url)
        }
        listAdapter.submit(items)
        binding.overlayEmpty.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        binding.overlayEmpty.text = getString(R.string.bookmarks_empty)
    }

    override fun onResume() {
        super.onResume()
        // 进入 overlay 时,首个可聚焦元素获得焦点
        binding.overlayList.post {
            binding.overlayList.findViewHolderForAdapterPosition(0)?.itemView?.requestFocus()
                ?: binding.btnCloseOverlay.requestFocus()
        }
        if (mode == Mode.TABS) refreshTabs()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val mode: Mode
        get() = Mode.valueOf(arguments?.getString(ARG_MODE) ?: Mode.TABS.name)

    companion object {
        private const val ARG_MODE = "mode"
        fun newInstance(mode: Mode): OverlayFragment =
            OverlayFragment().apply {
                arguments = Bundle().apply { putString(ARG_MODE, mode.name) }
            }
    }
}
