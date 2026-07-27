package com.tvfoxbrowser

import android.app.Application
import android.util.Log
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoRuntimeSettings
import java.io.File

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
        // 记录启动状态:写 "starting",onResume 时改为 "started"
        // 下次启动若发现仍是 "starting",说明在 Application.onCreate 之后
        // Activity.onResume 之前发生 native SIGSEGV(Java 抓不到),显示错误页
        StartupTracker.markStarting()
        initGeckoRuntime()
    }

    private fun initGeckoRuntime() {
        runCatching {
            // GeckoView 124 默认启用 WebRender + GPU compositor。
            // 国产 TV 的 GPU 驱动经常有 bug,compositor 启动时触发 native SIGSEGV,
            // Java 层 UncaughtExceptionHandler 抓不到,表现为「打开就闪退」且无崩溃日志。
            //
            // 通过 configFilePath 注入 prefs 强制软件渲染。
            //
            // 关键:Mozilla 官方文档(https://firefox-source-docs.mozilla.org/mobile/android/geckoview/consumer/automation.html)
            // 规定配置文件格式是 YAML,不是 JSON!之前写 JSON 根本没被读取,
            // 导致 GPU 崩溃仍然发生。
            //
            // YAML 格式:
            //   prefs:
            //     key: value
            //   args:
            //     - --arg
            //   env:
            //     VAR: value
            val geckoDir = getDir("gecko", 0).apply { if (!exists()) mkdirs() }
            val configFile = File(geckoDir, "geckoview-config.yaml")
            // 每次启动都重写,确保配置最新(避免旧版本残留坏文件)
            configFile.writeText(
                """
                prefs:
                  gfx.webrender.all: false
                  gfx.webrender.enabled: false
                  gfx.webrender.compositor: false
                  gfx.webrender.software: true
                  layers.acceleration.disabled: true
                  layers.gpu-process.enabled: false
                  layers.omtp.enabled: false
                  media.hardware-video-decoding.enabled: false
                  media.ffmpeg.vaapi.enabled: false
                  media.rdd-vpx.enabled: false
                  dom.ipc.processCount: 1
                  browser.tabs.remote.autostart: false
                  network.process.enabled: false
                  extensions.webextensions.remote: false
                args:
                  - --safe-mode
                """.trimIndent()
            )
            Log.i(TAG, "Wrote GeckoView YAML config to ${configFile.absolutePath}")

            // GeckoView 124 的 ContentBlocking.Settings.Builder 通过 categories(int)
            // 控制 ETP,没有 setEnhancedTrackingProtection 方法。
            val cbSettings = org.mozilla.geckoview.ContentBlocking.Settings.Builder()
                .build()

            val settings = GeckoRuntimeSettings.Builder()
                .javaScriptEnabled(SettingsManager.get().jsEnabled)
                .aboutConfigEnabled(false)
                .consoleOutput(false)
                .contentBlocking(cbSettings)
                .configFilePath(configFile.absolutePath)
                .build()

            geckoRuntime = GeckoRuntime.create(this, settings)
            Log.i(TAG, "GeckoRuntime initialized successfully (software rendering via YAML)")
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
