package com.tvfoxbrowser

import android.app.Application
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoRuntimeSettings

/**
 * Application 入口,负责初始化 GeckoRuntime (Firefox 内核运行时)。
 * 全进程唯一实例,所有标签页共用。
 */
class TvFoxApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initGeckoRuntime()
    }

    private fun initGeckoRuntime() {
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
    }

    companion object {
        @Volatile
        private var geckoRuntime: GeckoRuntime? = null

        @Volatile
        private var instance: TvFoxApp? = null

        @JvmStatic
        fun getRuntime(): GeckoRuntime =
            geckoRuntime ?: throw IllegalStateException("GeckoRuntime not initialized")

        @JvmStatic
        fun getApp(): TvFoxApp =
            instance ?: throw IllegalStateException("Application not initialized")
    }
}
