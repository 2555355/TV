package com.tvfoxbrowser

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * 历史记录管理。SharedPreferences + Gson 持久化,按时间倒序。
 * 重复 URL 更新时间与标题,限制最大条数避免膨胀。
 */
class HistoryManager private constructor(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREF, Context.MODE_PRIVATE)
    private val gson = Gson()
    private val type = object : TypeToken<List<Item>>() {}.type

    data class Item(
        val url: String,
        val title: String,
        val timestamp: Long
    )

    fun all(): List<Item> {
        val raw = prefs.getString(KEY, null) ?: return emptyList()
        return runCatching { gson.fromJson<List<Item>>(raw, type) ?: emptyList() }
            .getOrDefault(emptyList())
    }

    fun add(url: String, title: String) {
        if (url.isBlank() || url.startsWith("about:")) return
        if (SearchEngine.isSearchUrl(url) && title.isBlank()) return
        val list = all().toMutableList()
        list.removeAll { it.url == url }
        list.add(0, Item(url, title.ifBlank { url }, System.currentTimeMillis()))
        if (list.size > MAX) list.subList(MAX, list.size).clear()
        prefs.edit { putString(KEY, gson.toJson(list)) }
    }

    fun remove(url: String) {
        val list = all().toMutableList()
        list.removeAll { it.url == url }
        prefs.edit { putString(KEY, gson.toJson(list)) }
    }

    fun clear() = prefs.edit { remove(KEY) }

    companion object {
        private const val PREF = "tv_fox_history"
        private const val KEY = "items"
        private const val MAX = 500

        @Volatile
        private var instance: HistoryManager? = null

        fun get(): HistoryManager =
            instance ?: synchronized(this) {
                instance ?: HistoryManager(TvFoxApp.getApp()).also { instance = it }
            }

        fun init(context: Context) {
            if (instance == null) synchronized(this) {
                if (instance == null) instance = HistoryManager(context)
            }
        }
    }
}
