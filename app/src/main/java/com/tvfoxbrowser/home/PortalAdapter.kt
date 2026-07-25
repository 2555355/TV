package com.tvfoxbrowser.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tvfoxbrowser.BookmarkManager
import com.tvfoxbrowser.R

/**
 * 主页门户网格适配器。每张卡片 16:9,可被遥控器 D-pad 聚焦。
 */
class PortalAdapter : RecyclerView.Adapter<PortalAdapter.VH>() {

    private val items = mutableListOf<BookmarkManager.Item>()
    var onClick: ((BookmarkManager.Item) -> Unit)? = null

    fun submit(list: List<BookmarkManager.Item>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_portal_card, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.url.text = item.url.removePrefix("https://").removePrefix("http://")
        holder.itemView.setOnClickListener { onClick?.invoke(item) }
    }

    override fun getItemCount(): Int = items.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.card_title)
        val url: TextView = itemView.findViewById(R.id.card_url)
    }
}
