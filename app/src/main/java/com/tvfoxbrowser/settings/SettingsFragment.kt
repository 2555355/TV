package com.tvfoxbrowser.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.tvfoxbrowser.BookmarkManager
import com.tvfoxbrowser.HistoryManager
import com.tvfoxbrowser.R
import com.tvfoxbrowser.SettingsManager
import com.tvfoxbrowser.browser.WebViewEngine
import com.tvfoxbrowser.browser.TabManager

/**
 * 设置页。基于 PreferenceFragmentCompat。
 * - 搜索引擎 / UA 模式:写入 SettingsManager,并对所有现存 session 生效
 * - JS / DNT:写入 SettingsManager
 * - 清理:调用 GeckoEngine 的存储清理 + 本地历史清理
 */
class SettingsFragment : DialogFragment() {

    private var tabManager: TabManager? = null
    var onClose: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_TvFoxBrowser)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        view.findViewById<View>(R.id.btn_close_settings).setOnClickListener {
            onClose?.invoke()
        }
        childFragmentManager.beginTransaction()
            .replace(R.id.settings_container, PrefsFragment())
            .commit()
        return view
    }

    fun bindTabManager(tm: TabManager) {
        tabManager = tm
    }

    /** UA 模式变更后,对所有现存 WebView 重新应用 */
    fun reapplyUaOnAllTabs() {
        tabManager?.allTabs?.forEach { tab ->
            tab.webView?.let { WebViewEngine.applyUa(it) }
        }
    }

    class PrefsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
            // 同步当前值到 Preference
            findPreference<ListPreference>("search_engine")?.let { p ->
                p.value = SettingsManager.get().searchEngineId
                p.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { pref, value ->
                    SettingsManager.get().searchEngineId = value.toString()
                    p.value = value.toString()
                    true
                }
            }
            findPreference<ListPreference>("ua_mode")?.let { p ->
                p.value = SettingsManager.get().uaMode
                p.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { pref, value ->
                    SettingsManager.get().uaMode = value.toString()
                    p.value = value.toString()
                    (parentFragment as? SettingsFragment)?.reapplyUaOnAllTabs()
                    true
                }
            }
            findPreference<SwitchPreference>("js_enabled")?.let { p ->
                p.isChecked = SettingsManager.get().jsEnabled
                p.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { pref, value ->
                    SettingsManager.get().jsEnabled = value as Boolean
                    true
                }
            }
            findPreference<SwitchPreference>("dnt")?.let { p ->
                p.isChecked = SettingsManager.get().doNotTrack
                p.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { pref, value ->
                    SettingsManager.get().doNotTrack = value as Boolean
                    true
                }
            }

            findPreference<Preference>("clear_cache")?.setOnPreferenceClickListener {
                WebViewEngine.clearCache()
                toast(R.string.settings_cleared)
                true
            }
            findPreference<Preference>("clear_cookies")?.setOnPreferenceClickListener {
                WebViewEngine.clearCookies()
                toast(R.string.settings_cleared)
                true
            }
            findPreference<Preference>("clear_history")?.setOnPreferenceClickListener {
                HistoryManager.get().clear()
                toast(R.string.settings_cleared)
                true
            }
            findPreference<Preference>("clear_all")?.setOnPreferenceClickListener {
                WebViewEngine.clearAllBrowsingData()
                HistoryManager.get().clear()
                toast(R.string.settings_cleared)
                true
            }
        }

        private fun toast(resId: Int) {
            Toast.makeText(requireContext(), resId, Toast.LENGTH_SHORT).show()
        }
    }
}
