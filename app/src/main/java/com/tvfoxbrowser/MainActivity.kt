package com.tvfoxbrowser

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.LinearLayout
import android.widget.TextView
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
 *
 * 海尔 HRA920L (Android 5.1) 上反复闪退,这里把 onCreate 整体包 try-catch:
 * 任何启动期异常都会落到 CrashHandler 的日志里(由 Application 安装),
 * 同时显示一个最简单的错误页,避免黑屏直接退出。
 */
class MainActivity : AppCompatActivity() {

    private var browserFragment: BrowserFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            // 初始化各 Manager(底层用 SharedPreferences + Gson)
            // TvFoxApp.onCreate 已经初始化过一次,这里再 init 是幂等的,
            // 防止某些 ROM 上 Application.onCreate 被跳过的情况。
            runCatching { SettingsManager.init(applicationContext) }
            runCatching { HistoryManager.init(applicationContext) }
            runCatching { BookmarkManager.init(applicationContext) }

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
        } catch (t: Throwable) {
            // 兜底:把异常写到 CrashHandler 目录,并显示最简单的错误页。
            Log.e(TAG, "MainActivity onCreate failed", t)
            showError(t)
        }
    }

    /** 启动失败时显示最简单的错误页(只依赖 TextView,不会再次抛异常) */
    private fun showError(t: Throwable) {
        runCatching {
            val sw = java.io.StringWriter()
            t.printStackTrace(java.io.PrintWriter(sw))
            val container = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(48, 48, 48, 48)
            }
            val tv = TextView(this).apply {
                text = "浏览器启动失败,请把崩溃日志反馈给开发者:\n\n" +
                    "/Android/data/$packageName/files/crash/latest.txt\n\n" +
                    sw.toString().take(2000)
                textSize = 14f
                setTextColor(0xFFFFFFFF.toInt())
            }
            container.addView(tv)
            setContentView(container)
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

    companion object {
        private const val TAG = "MainActivity"
    }
}
