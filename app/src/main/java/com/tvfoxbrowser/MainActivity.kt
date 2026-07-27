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
import org.xwalk.core.XWalkInitializer

/**
 * 入口 Activity。
 * - 托管 BrowserFragment
 * - 处理遥控器 BACK / MENU / SEARCH 按键
 * - 初始化各 Manager
 * - 异步初始化 Crosswalk 引擎(必须完成才能创建 XWalkView)
 *
 * Crosswalk 初始化流程:
 * 1. XWalkInitializer.initAsync() 异步下载/加载 Crosswalk 运行时
 * 2. 完成后回调 onXWalkInitComplete()
 * 3. 此时才能创建 XWalkView 并加载页面
 *
 * 海尔 HRA920L (Android 5.1) 上反复闪退,这里把 onCreate 整体包 try-catch。
 */
class MainActivity : AppCompatActivity(), XWalkInitializer.XWalkInitListener {

    private var browserFragment: BrowserFragment? = null
    private var xWalkInitializer: XWalkInitializer? = null
    private var crosswalkReady = false
    private var savedInstanceStateBundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            // 初始化各 Manager(底层用 SharedPreferences + Gson)
            runCatching { SettingsManager.init(applicationContext) }
            runCatching { HistoryManager.init(applicationContext) }
            runCatching { BookmarkManager.init(applicationContext) }

            setContentView(R.layout.activity_main)

            // 把 Activity 注入 WebViewEngine(XWalkView 构造需要 Activity)
            WebViewEngine.currentActivity = this

            savedInstanceStateBundle = savedInstanceState

            // 初始化 Crosswalk 引擎(异步,可能需要下载运行时)
            // 这一步是 Crosswalk 替换系统 WebView 的关键
            xWalkInitializer = XWalkInitializer(this, this).also {
                it.initAsync()
            }

            // 立即显示 UI 框架(但 BrowserFragment 暂不创建,等 Crosswalk 就绪)
            // 如果 Crosswalk 初始化失败,会在 onXWalkInitFailed 里降级处理
        } catch (t: Throwable) {
            Log.e(TAG, "MainActivity onCreate failed", t)
            showError(t)
        }
    }

    /** Crosswalk 初始化开始 */
    override fun onXWalkInitStarted() {
        Log.d(TAG, "XWalk init started")
    }

    /** Crosswalk 初始化完成,可以创建 XWalkView 了 */
    override fun onXWalkInitCompleted() {
        Log.i(TAG, "XWalk init complete, creating BrowserFragment")
        crosswalkReady = true
        try {
            if (savedInstanceStateBundle == null) {
                browserFragment = BrowserFragment()
                supportFragmentManager.commit {
                    replace(R.id.main_container, browserFragment!!)
                }
            } else {
                browserFragment = supportFragmentManager
                    .findFragmentById(R.id.main_container) as? BrowserFragment
            }
        } catch (t: Throwable) {
            Log.e(TAG, "BrowserFragment creation failed", t)
            showError(t)
        }
    }

    /** Crosswalk 初始化失败(可能是 ROM 不兼容或缺少依赖) */
    override fun onXWalkInitFailed() {
        Log.e(TAG, "XWalk init failed!")
        runOnUiThread {
            try {
                val container = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(48, 48, 48, 48)
                }
                val tv = TextView(this).apply {
                    text = "浏览器内核初始化失败。\n\n" +
                        "可能原因:\n" +
                        "1. 系统 ROM 不兼容 Crosswalk\n" +
                        "2. 存储空间不足\n" +
                        "3. Android 5.1 系统库缺失\n\n" +
                        "崩溃日志:/Android/data/$packageName/files/crash/latest.txt"
                    textSize = 14f
                    setTextColor(0xFFFFFFFF.toInt())
                }
                container.addView(tv)
                setContentView(container)
            } catch (_: Throwable) {}
        }
    }

    /** 用户取消 Crosswalk 初始化(罕见,通常不会触发) */
    override fun onXWalkInitCancelled() {
        Log.w(TAG, "XWalk init cancelled")
        finish()
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
        // 解除 Activity 引用,避免泄漏
        if (WebViewEngine.currentActivity === this) {
            WebViewEngine.currentActivity = null
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
