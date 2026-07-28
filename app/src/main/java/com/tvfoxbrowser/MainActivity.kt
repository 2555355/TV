package com.tvfoxbrowser

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.tvfoxbrowser.browser.WebViewEngine
import com.tvfoxbrowser.ui.BrowserFragment

/**
 * 入口 Activity。
 * - 托管 BrowserFragment
 * - 处理遥控器 BACK / MENU / SEARCH 按键
 * - 初始化各 Manager
 * - 初始化 GeckoRuntime(GeckoView 的全局运行时,同步创建)
 *
 * GeckoView 初始化流程(比 Crosswalk 简单):
 * 1. GeckoRuntime.create(context) 同步创建全局运行时(只创建一次)
 * 2. 每个 GeckoSession.open(runtime) 关联会话与运行时
 * 3. GeckoView.setSession(session) 关联视图与会话
 *
 * 海尔 HRA920L (Android 5.1) 上反复闪退,这里把 onCreate 整体包 try-catch。
 */
class MainActivity : AppCompatActivity() {

    private var browserFragment: BrowserFragment? = null
    private var geckoReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            // 初始化各 Manager(底层用 SharedPreferences + Gson)
            runCatching { SettingsManager.init(applicationContext) }
            runCatching { HistoryManager.init(applicationContext) }
            runCatching { BookmarkManager.init(applicationContext) }

            setContentView(R.layout.activity_main)

            // 把 Activity 注入 WebViewEngine
            WebViewEngine.currentActivity = this

            // 初始化 GeckoRuntime(同步,失败时显示错误页)
            WebViewEngine.onRuntimeFailed = {
                runOnUiThread { showRuntimeError() }
            }
            WebViewEngine.initRuntime(applicationContext)
            geckoReady = WebViewEngine.isRuntimeReady()

            if (!geckoReady) {
                showRuntimeError()
                return
            }

            // GeckoRuntime 就绪,创建 BrowserFragment
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
            Log.e(TAG, "MainActivity onCreate failed", t)
            showError(t)
        }
    }

    /** GeckoRuntime 初始化失败时显示错误页 */
    private fun showRuntimeError() {
        runOnUiThread {
            try {
                val container = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(48, 48, 48, 48)
                }
                val tv = TextView(this).apply {
                    text = "Gecko 浏览器内核初始化失败。\n\n" +
                        "可能原因:\n" +
                        "1. 系统 ROM 不兼容 GeckoView 69\n" +
                        "2. 存储空间不足(GeckoView 需要 ~60MB)\n" +
                        "3. Android 5.1 系统库缺失\n" +
                        "4. 设备 RAM 不足(GeckoView 至少需 500MB 可用)\n\n" +
                        "崩溃日志:/Android/data/$packageName/files/crash/latest.txt"
                    textSize = 14f
                    setTextColor(0xFFFFFFFF.toInt())
                }
                container.addView(tv)
                setContentView(container)
            } catch (_: Throwable) {}
        }
    }

    /** 启动失败时显示最简单的错误页 */
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

    override fun onDestroy() {
        super.onDestroy()
        if (WebViewEngine.currentActivity === this) {
            WebViewEngine.currentActivity = null
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
