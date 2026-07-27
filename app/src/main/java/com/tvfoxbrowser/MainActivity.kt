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
            when {
                !TvFoxApp.isRuntimeReady() -> {
                    // GeckoRuntime 初始化失败 -> 显示内核错误页
                    showFragment(ErrorFragment.newInstance(ErrorFragment.Mode.RUNTIME_INIT))
                }
                CrashHandler.readLog() != null -> {
                    // 上次崩溃过 -> 显示崩溃页一次。
                    // ErrorFragment 显示日志后会自行清除磁盘日志,
                    // 避免用户关闭后再打开仍卡在崩溃页(死循环)。
                    // 若浏览器再次崩溃,会重新写入日志,下次启动再次显示。
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
