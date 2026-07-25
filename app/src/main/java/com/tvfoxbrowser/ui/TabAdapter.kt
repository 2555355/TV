package com.tvfoxbrowser.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tvfoxbrowser.R
import com.tvfoxbrowser.browser.Tab

/** 标签页缩略图网格适配器,带关闭按钮 */
class TabAdapter : RecyclerView.Adapter<TabAdapter.VH>() {

    private val tabs = mutableListOf<Tab>()
    var activeTabId: Long = -1
    var onSelect: ((Tab) -> Unit)? = null
    var onClose: ((Tab) -> Unit)? = null

    fun submit(list: List<Tab>, activeId: Long) {
        tabs.clear()
        tabs.addAll(list)
        activeTabId = activeId
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tab, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val tab = tabs[position]
        holder.title.text = tab.displayTitle
        // 高亮当前标签
        holder.card.alpha = if (tab.id == activeTabId) 1.0f else 0.6f
        holder.card.setOnClickListener { onSelect?.invoke(tab) }
        holder.btnClose.setOnClickListener { onClose?.invoke(tab) }
        holder.card.setOnFocusChangeListener { _, hasFocus ->
            holder.card.alpha = if (hasFocus || tab.id == activeTabId) 1.0f else 0.6f
        }
    }

    override fun getItemCount(): Int = tabs.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: View = itemView.findViewById(R.id.tab_card)
        val title: TextView = itemView.findViewById(R.id.tab_title_overlay)
        val btnClose: ImageButton = itemView.findViewById(R.id.btn_close_tab)
    }
}
