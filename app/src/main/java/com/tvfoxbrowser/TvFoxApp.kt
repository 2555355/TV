package com.tvfoxbrowser

import android.app.Application
import android.util.Log
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoRuntimeSettings

/**
 * Application 入口,负责初始化 GeckoRuntime (Firefox 内核运行时)。
 * 全进程唯一实例,所有标签页共用。
 *
 * 初始化失败不崩溃,记录错误并保持 geckoRuntime=null,
 * 由 UI 层(BrowserFragment)显示错误提示。
 */
class TvFoxApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        // 先安装崩溃日志捕获,确保后续任何崩溃都能记录到文件
        CrashHandler.install(this)
        initGeckoRuntime()
    }

    private fun initGeckoRuntime() {
        runCatching {
            // GeckoView 124 的 ContentBlocking.Settings.Builder 通过 categories(int)
            // 控制 ETP,没有 setEnhancedTrackingProtection 方法。
            // DNT/ETP 级别在 session 层用 useTrackingProtection 控制(见 GeckoEngine)。
            val cbSettings = org.mozilla.geckoview.ContentBlocking.Settings.Builder()
                .build()

            val settings = GeckoRuntimeSettings.Builder()
                .javaScriptEnabled(SettingsManager.get().jsEnabled)
                .aboutConfigEnabled(false)
                .consoleOutput(false)
                .contentBlocking(cbSettings)
                .configFilePath(getDir("gecko", 0).path)
                .build()

            geckoRuntime = GeckoRuntime.create(this, settings)
            Log.i(TAG, "GeckoRuntime initialized successfully")
        }.onFailure { e ->
            Log.e(TAG, "GeckoRuntime init FAILED", e)
            runtimeInitError = e
        }
    }

    companion object {
        private const val TAG = "TvFoxApp"

        @Volatile
        private var geckoRuntime: GeckoRuntime? = null

        @Volatile
        private var instance: TvFoxApp? = null

        @Volatile
        private var runtimeInitError: Throwable? = null

        @JvmStatic
        fun getRuntime(): GeckoRuntime =
            geckoRuntime ?: throw IllegalStateException(
                "GeckoRuntime not initialized", runtimeInitError
            )

        /** GeckoRuntime 是否初始化成功(供 UI 层判断是否显示错误页) */
        @JvmStatic
        fun isRuntimeReady(): Boolean = geckoRuntime != null

        /** GeckoRuntime 初始化失败的错误信息(供 UI 层显示) */
        @JvmStatic
        fun getRuntimeError(): Throwable? = runtimeInitError

        @JvmStatic
        fun getApp(): TvFoxApp =
            instance ?: throw IllegalStateException("Application not initialized")
    }
}
