package com.tvfoxbrowser

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * 全局设置管理,基于 SharedPreferences。
 * 持久化:搜索引擎、UA 模式、JS 开关、DNT、首页快捷入口。
 */
class SettingsManager private constructor(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    /** 默认搜索引擎 id,见 SearchEngine */
    var searchEngineId: String
        get() = prefs.getString(KEY_SEARCH_ENGINE, DEFAULT_SEARCH_ENGINE) ?: DEFAULT_SEARCH_ENGINE
        set(value) = prefs.edit { putString(KEY_SEARCH_ENGINE, value) }

    /** UA 模式: mobile / desktop / tv
     *  默认 mobile:Crosswalk 内核是 Chromium 53(2016),不支持 CSS Grid /
     *  flex-gap / sticky 等现代特性,桌面版网站 UI 会错乱。手机版页面用更老
     *  的 CSS(flexbox 简单布局),兼容性好很多。用户可在设置里切到 desktop。 */
    var uaMode: String
        get() = prefs.getString(KEY_UA_MODE, UA_MOBILE) ?: UA_MOBILE
        set(value) = prefs.edit { putString(KEY_UA_MODE, value) }

    var jsEnabled: Boolean
        get() = prefs.getBoolean(KEY_JS, true)
        set(value) = prefs.edit { putBoolean(KEY_JS, value) }

    var doNotTrack: Boolean
        get() = prefs.getBoolean(KEY_DNT, false)
        set(value) = prefs.edit { putBoolean(KEY_DNT, value) }

    companion object {
        private const val PREF_NAME = "tv_fox_prefs"
        private const val KEY_SEARCH_ENGINE = "search_engine"
        private const val KEY_UA_MODE = "ua_mode"
        private const val KEY_JS = "js_enabled"
        private const val KEY_DNT = "dnt"

        const val UA_MOBILE = "mobile"
        const val UA_DESKTOP = "desktop"
        const val UA_TV = "tv"

        const val DEFAULT_SEARCH_ENGINE = "bing"

        @Volatile
        private var instance: SettingsManager? = null

        fun get(): SettingsManager =
            instance ?: synchronized(this) {
                instance ?: SettingsManager(TvFoxApp.getApp()).also { instance = it }
            }

        fun init(context: Context) {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SettingsManager(context)
                    }
                }
            }
        }
    }
}
