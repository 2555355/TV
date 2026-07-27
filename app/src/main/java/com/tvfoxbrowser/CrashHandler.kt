package com.tvfoxbrowser

import android.content.Context
import android.os.Build
import android.os.Process
import android.util.Log
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 全局崩溃日志收集器。
 *
 * 为什么需要它:
 * 用户设备是海尔 HRA920L(Android 5.1,可用内存 434MB),
 * 反复"还是闪退"但拿不到 logcat,无法定位真正的崩溃点。
 * 本类把 Java 层未捕获异常的堆栈写到 App 私有目录的 crash/ 子目录,
 * 用户用文件管理器即可取出反馈。
 *
 * 日志路径:
 * /Android/data/com.tvfoxbrowser/files/crash/crash_YYYYMMDD_HHMMSS.txt
 *
 * 同时保留系统默认处理器,让进程仍然按原流程退出。
 */
class CrashHandler private constructor(
    private val context: Context
) : Thread.UncaughtExceptionHandler {

    private val defaultHandler: Thread.UncaughtExceptionHandler? =
        Thread.getDefaultUncaughtExceptionHandler()

    private val timeFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)

    fun install() {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        // 1. 把崩溃堆栈写到文件
        runCatching { writeCrashToFile(t, e) }
        // 2. 打印到 logcat(方便 adb 调试时也能看到)
        runCatching { Log.e(TAG, "Uncaught exception on thread ${t.name}", e) }
        // 3. 交给系统默认处理(通常会让进程终止)
        defaultHandler?.uncaughtException(t, e)
    }

    private fun writeCrashToFile(t: Thread, e: Throwable) {
        val dir = File(context.getExternalFilesDir(null) ?: context.filesDir, "crash")
        if (!dir.exists()) dir.mkdirs()

        val sw = StringWriter()
        e.printStackTrace(PrintWriter(sw))
        val stack = sw.toString()

        val sb = StringBuilder()
        sb.append("===== TVFoxBrowser Crash Report =====\n")
        sb.append("Time       : ").append(timeFormat.format(Date())).append("\n")
        sb.append("Thread     : ").append(t.name).append("\n")
        sb.append("Process    : ${android.os.Process.myPid()}\n")
        sb.append("Package    : ").append(context.packageName).append("\n")
        sb.append("App Version: ").append(getVersionName()).append(" (").append(getVersionCode()).append(")\n")
        sb.append("Manufacturer: ").append(Build.MANUFACTURER).append("\n")
        sb.append("Model      : ").append(Build.MODEL).append("\n")
        sb.append("Brand      : ").append(Build.BRAND).append("\n")
        sb.append("Device     : ").append(Build.DEVICE).append("\n")
        sb.append("Product    : ").append(Build.PRODUCT).append("\n")
        sb.append("Android    : ").append(Build.VERSION.RELEASE)
            .append(" (API ").append(Build.VERSION.SDK_INT).append(")\n")
        sb.append("ABI        : ").append(Build.SUPPORTED_ABIS?.joinToString(",") ?: "unknown").append("\n")
        sb.append("Total Mem  : see /proc/meminfo\n")
        sb.append("\n----- Stack Trace -----\n")
        sb.append(stack)
        sb.append("\n----- End of Report -----\n")

        val file = File(dir, "crash_${timeFormat.format(Date())}.txt")
        file.writeText(sb.toString())

        // 同时写一份 latest.txt,便于用户找到最新一次
        File(dir, "latest.txt").writeText(sb.toString())
    }

    private fun getVersionName(): String = runCatching {
        context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "unknown"
    }.getOrDefault("unknown")

    private fun getVersionCode(): Long = runCatching {
        val pi = context.packageManager.getPackageInfo(context.packageName, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) pi.longVersionCode else @Suppress("DEPRECATION") pi.versionCode.toLong()
    }.getOrDefault(-1L)

    companion object {
        private const val TAG = "CrashHandler"

        @Volatile
        private var instance: CrashHandler? = null

        fun install(context: Context) {
            if (instance != null) return
            synchronized(this) {
                if (instance == null) {
                    instance = CrashHandler(context.applicationContext).also { it.install() }
                }
            }
        }
    }
}
