package com.tvfoxbrowser

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.tvfoxbrowser.ui.BrowserFragment

/**
 * 入口 Activity。
 * - 托管 BrowserFragment
 * - 处理遥控器 BACK / MENU / SEARCH 按键
 * - 初始化各 Manager
 *
 * D-pad 方向键导航由 Android 焦点框架自动处理,无需手写。
 */
class MainActivity : AppCompatActivity() {

    private var browserFragment: BrowserFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初始化各 Manager(底层用 SharedPreferences + Gson)
        SettingsManager.init(applicationContext)
        HistoryManager.init(applicationContext)
        BookmarkManager.init(applicationContext)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            browserFragment = BrowserFragment()
            supportFragmentManager.commit {
                replace(R.id.main_container, browserFragment!!)
            }
        } else {
            browserFragment = supportFragmentManager
                .findFragmentById(R.id.main_container) as? BrowserFragment
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                if (browserFragment?.onBackPressed() == true) return true
            }
            KeyEvent.KEYCODE_MENU, KeyEvent.KEYCODE_SETTINGS -> {
                browserFragment?.showSettings()
                return true
            }
            KeyEvent.KEYCODE_SEARCH -> {
                // 聚焦地址栏,便于遥控器直接输入
                // BrowserFragment 的地址栏会通过 requestFocus 处理
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (browserFragment?.onBackPressed() != true) {
            @Suppress("DEPRECATION")
            super.onBackPressed()
        }
    }
}
