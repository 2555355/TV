package com.tvfoxbrowser.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tvfoxbrowser.R

/** 通用列表项适配器:历史记录 / 书签 */
class ListItemAdapter(
    private val showClose: Boolean = false
) : RecyclerView.Adapter<ListItemAdapter.VH>() {

    data class Item(val title: String, val subtitle: String, val tag: Any?)

    private val items = mutableListOf<Item>()
    var onClick: ((Item) -> Unit)? = null
    var onClose: ((Item) -> Unit)? = null

    fun submit(list: List<Item>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.subtitle.text = item.subtitle
        holder.itemView.setOnClickListener { onClick?.invoke(item) }
    }

    override fun getItemCount(): Int = items.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.item_title)
        val subtitle: TextView = itemView.findViewById(R.id.item_url)
    }
}
