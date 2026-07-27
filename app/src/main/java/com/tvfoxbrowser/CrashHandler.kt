package com.tvfoxbrowser

import android.os.Build
import android.util.Log
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 全局未捕获异常处理器。
 *
 * 把崩溃栈写入应用私有目录 getExternalFilesDir(null)/crash.log,
 * 用户可通过文件管理器在 Android/data/com.tvfoxbrowser/files/ 下找到。
 *
 * 同时打印到 logcat 方便 adb 调试。
 *
 * 安装后,旧 crash.log 会被覆盖;只保留最近一次崩溃。
 */
object CrashHandler : Thread.UncaughtExceptionHandler {

    private const val TAG = "TvFoxCrash"
    private const val FILE_NAME = "crash.log"
    private const val MAX_LOG_SIZE = 512 * 1024 // 512KB 上限,避免膨胀

    private var previousHandler: Thread.UncaughtExceptionHandler? = null
    private var appVersion: String = "unknown"
    private var appVersionCode: Int = 0

    fun install(app: TvFoxApp) {
        appVersion = packageVersionName(app)
        appVersionCode = packageVersionCode(app)
        previousHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
        Log.i(TAG, "CrashHandler installed. Log dir: ${logDir(app)}")
    }

    private fun logDir(app: TvFoxApp): File {
        // 应用私有外部目录,无需权限,用户可通过文件管理器访问
        // Android/data/com.tvfoxbrowser/files/
        return app.getExternalFilesDir(null) ?: app.filesDir
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        // 1. 打印到 logcat
        Log.e(TAG, "Uncaught exception on thread ${t.name}", e)

        // 2. 写入文件
        runCatching {
            val report = buildReport(t, e)
            val dir = logDir(TvFoxApp.getApp())
            if (!dir.exists()) dir.mkdirs()
            val file = File(dir, FILE_NAME)
            // 超过上限则清空重写,避免无限增长
            if (file.exists() && file.length() > MAX_LOG_SIZE) {
                file.delete()
            }
            file.appendText("\n\n==========\n")
            file.appendText(report)
        }

        // 3. 交给系统默认处理器(让进程正常退出)
        previousHandler?.uncaughtException(t, e)
    }

    private fun buildReport(t: Thread, e: Throwable): String {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        pw.println(SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US).format(Date()))
        pw.println("App: TV Fox Browser v$appVersion ($appVersionCode)")
        pw.println("Device: ${Build.MANUFACTURER} ${Build.MODEL}")
        pw.println("Android: ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})")
        pw.println("ABI: ${Build.SUPPORTED_ABIS.joinToString(",")}")
        pw.println("Thread: ${t.name} (id=${t.id})")
        pw.println("Process: ${android.os.Process.myPid()}")
        pw.println()
        pw.println("Stacktrace:")
        e.printStackTrace(pw)
        // 包含所有 cause
        var cause: Throwable? = e.cause
        while (cause != null) {
            pw.println()
            pw.println("Caused by: ${cause.javaClass.name}: ${cause.message}")
            cause.printStackTrace(pw)
            cause = cause.cause
        }
        pw.flush()
        return sw.toString()
    }

    private fun packageVersionName(app: TvFoxApp): String =
        runCatching {
            app.packageManager.getPackageInfo(app.packageName, 0).versionName ?: "unknown"
        }.getOrDefault("unknown")

    private fun packageVersionCode(app: TvFoxApp): Int =
        runCatching {
            val pi = app.packageManager.getPackageInfo(app.packageName, 0)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                pi.longVersionCode.toInt()
            } else {
                @Suppress("DEPRECATION")
                pi.versionCode
            }
        }.getOrDefault(0)

    /** 读取已保存的崩溃日志(供诊断界面/分享用),无则返回 null */
    fun readLog(): String? = runCatching {
        val file = File(logDir(TvFoxApp.getApp()), FILE_NAME)
        if (file.exists()) file.readText() else null
    }.getOrNull()

    /** 清除崩溃日志 */
    fun clearLog() {
        runCatching {
            File(logDir(TvFoxApp.getApp()), FILE_NAME).delete()
        }
    }
}
