package com.tvfoxbrowser

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * 书签管理。同时作为主页门户的快捷入口。
 * 内置一份默认 TV 门户站点,首次启动写入。
 */
class BookmarkManager private constructor(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREF, Context.MODE_PRIVATE)
    private val gson = Gson()
    private val type = object : TypeToken<List<Item>>() {}.type

    data class Item(
        val url: String,
        val title: String,
        val builtin: Boolean = false
    )

    fun all(): List<Item> {
        val raw = prefs.getString(KEY, null)
        if (raw == null) {
            val defaults = defaultPortals()
            save(defaults)
            return defaults
        }
        return runCatching { gson.fromJson<List<Item>>(raw, type) ?: defaultPortals() }
            .getOrDefault(defaultPortals())
    }

    fun add(url: String, title: String) {
        if (url.isBlank()) return
        val list = all().toMutableList()
        if (list.any { it.url == url }) return
        list.add(Item(url, title.ifBlank { url }, builtin = false))
        save(list)
    }

    fun remove(url: String) {
        val list = all().toMutableList()
        val target = list.firstOrNull { it.url == url } ?: return
        if (target.builtin) return // 内置项不可删
        list.removeAll { it.url == url }
        save(list)
    }

    fun contains(url: String): Boolean = all().any { it.url == url }

    private fun save(list: List<Item>) {
        prefs.edit { putString(KEY, gson.toJson(list)) }
    }

    private fun defaultPortals(): List<Item> = listOf(
        Item("https://www.youtube.com", TvFoxApp.getApp().getString(R.string.site_youtube), builtin = true),
        Item("https://www.bilibili.com", TvFoxApp.getApp().getString(R.string.site_bilibili), builtin = true),
        Item("https://www.youku.com", TvFoxApp.getApp().getString(R.string.site_youku), builtin = true),
        Item("https://www.iqiyi.com", TvFoxApp.getApp().getString(R.string.site_iqiyi), builtin = true),
        Item("https://www.baidu.com", TvFoxApp.getApp().getString(R.string.site_baidu), builtin = true),
        Item("https://www.taobao.com", TvFoxApp.getApp().getString(R.string.site_taobao), builtin = true),
        Item("https://www.jd.com", TvFoxApp.getApp().getString(R.string.site_jd), builtin = true),
        Item("https://www.zhihu.com", TvFoxApp.getApp().getString(R.string.site_zhihu), builtin = true)
    )

    companion object {
        private const val PREF = "tv_fox_bookmarks"
        private const val KEY = "items"

        @Volatile
        private var instance: BookmarkManager? = null

        fun get(): BookmarkManager =
            instance ?: synchronized(this) {
                instance ?: BookmarkManager(TvFoxApp.getApp()).also { instance = it }
            }

        fun init(context: Context) {
            if (instance == null) synchronized(this) {
                if (instance == null) instance = BookmarkManager(context)
            }
        }
    }
}
