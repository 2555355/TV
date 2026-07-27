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
            // 关键修复:传入 --safe-mode 参数。
            // 国产电视/电视盒的 GPU 驱动有 bug,GeckoView 默认启用 WebRender + GPU
            // compositor,会在启动后几秒触发 native SIGSEGV(Java 层抓不到,直接闪退)。
            // --safe-mode 会禁用硬件加速、WebGL,强制软件渲染,避免 GPU 驱动崩溃。
            // 副作用:JIT 也被禁用,JS 执行变慢,但能保证不闪退。
            // 用 arguments API(公开稳定),比 configFilePath 可靠(configFilePath 默认
            // 只在 debuggable=true 时读取,显式指定路径又涉及文件写入,容易出错)。
            .arguments(arrayOf("--safe-mode"))
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
