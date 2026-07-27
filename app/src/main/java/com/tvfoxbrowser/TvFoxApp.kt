package com.tvfoxbrowser

import android.app.Application
import android.util.Log

/**
 * Application 入口。
 *
 * 换用系统 WebView 后,这里不再需要初始化 GeckoRuntime。
 * WebView 由系统提供,每个 Tab 创建独立的 WebView 实例(见 WebViewEngine)。
 *
 * 海尔 HRA920L (Android 5.1, 434MB 可用内存) 上反复闪退,但拿不到 logcat。
 * 这里安装 CrashHandler,把所有未捕获异常写到 crash/ 目录,便于事后排查。
 */
class TvFoxApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // 第一件事:安装崩溃捕获,确保后续任何初始化失败都能记录
        runCatching { CrashHandler.install(this) }
        instance = this

        // 各 Manager 初始化都包一层 runCatching:
        // 任何单一 Manager 失败都不应让整个 App 拒绝启动。
        runCatching { SettingsManager.init(applicationContext) }
            .onFailure { Log.e(TAG, "SettingsManager init failed", it) }
        runCatching { HistoryManager.init(applicationContext) }
            .onFailure { Log.e(TAG, "HistoryManager init failed", it) }
        runCatching { BookmarkManager.init(applicationContext) }
            .onFailure { Log.e(TAG, "BookmarkManager init failed", it) }
    }

    companion object {
        private const val TAG = "TvFoxApp"

        @Volatile
        private var instance: TvFoxApp? = null

        @JvmStatic
        fun getApp(): TvFoxApp =
            instance ?: throw IllegalStateException("Application not initialized")
    }
}
