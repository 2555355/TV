package com.tvfoxbrowser

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.tvfoxbrowser.ui.BrowserFragment
import com.tvfoxbrowser.ui.ErrorFragment

/**
 * 入口 Activity。
 * - 托管 BrowserFragment 或 ErrorFragment(内核失败时)
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
            // 检查上次是否有崩溃记录(若有,显示崩溃页而非直接进浏览器)
            val lastCrash = CrashHandler.readLog()
            when {
                !TvFoxApp.isRuntimeReady() -> {
                    // GeckoRuntime 初始化失败 -> 显示内核错误页
                    showFragment(ErrorFragment.newInstance(ErrorFragment.Mode.RUNTIME_INIT))
                }
                lastCrash != null -> {
                    // 上次崩溃过 -> 显示崩溃页(用户可查看日志或重启)
                    showFragment(ErrorFragment.newInstance(ErrorFragment.Mode.CRASH))
                }
                else -> {
                    // 正常启动 -> 进入浏览器
                    browserFragment = BrowserFragment()
                    showFragment(browserFragment!!)
                }
            }
        } else {
            browserFragment = supportFragmentManager
                .findFragmentById(R.id.main_container) as? BrowserFragment
        }
    }

    private fun showFragment(frag: androidx.fragment.app.Fragment) {
        supportFragmentManager.commit {
            replace(R.id.main_container, frag)
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
