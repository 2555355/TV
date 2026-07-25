package com.tvfoxbrowser.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.tvfoxbrowser.BookmarkManager
import com.tvfoxbrowser.R
import com.tvfoxbrowser.SearchEngine
import com.tvfoxbrowser.databinding.FragmentHomeBinding

/**
 * 主页门户:搜索框 + 16:9 快捷入口网格。
 * 由 BrowserFragment 在 active tab 为 about:home 时显示。
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapter = PortalAdapter()

    var onNavigate: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 16:9 适配:横向 4 列,卡片宽 220dp
        val span = computeSpan()
        val lm = GridLayoutManager(requireContext(), span)
        binding.portalGrid.layoutManager = lm
        binding.portalGrid.adapter = adapter
        // 间距
        binding.portalGrid.addItemDecoration(
            com.tvfoxbrowser.ui.GridSpacingItemDecoration(
                span,
                resources.getDimensionPixelSize(R.dimen.card_spacing),
                includeEdge = true
            )
        )

        adapter.onClick = { item ->
            onNavigate?.invoke(item.url)
        }

        binding.homeSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                val text = v.text.toString().trim()
                if (text.isNotEmpty()) {
                    onNavigate?.invoke(SearchEngine.normalize(text))
                }
                true
            } else false
        }

        loadPortals()
    }

    private fun computeSpan(): Int {
        val widthDp = resources.configuration.screenWidthDp
        // 每张卡片约 220dp + 16dp 间距,4 列适合 1080p 16:9
        return when {
            widthDp >= 800 -> 4
            widthDp >= 500 -> 3
            else -> 2
        }
    }

    private fun loadPortals() {
        adapter.submit(BookmarkManager.get().all())
    }

    fun refreshPortals() = loadPortals()

    override fun onResume() {
        super.onResume()
        // 首次进入聚焦搜索框,方便遥控器直接输入
        binding.homeSearch.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
