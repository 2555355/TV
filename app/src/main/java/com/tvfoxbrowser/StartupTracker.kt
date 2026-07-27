package com.tvfoxbrowser

import android.content.Context
import androidx.core.content.edit

/**
 * 启动状态追踪,用于检测 Java 层抓不到的 native SIGSEGV 崩溃。
 *
 * 流程:
 * 1. Application.onCreate 调用 markStarting(),写入 STARTING
 * 2. Activity.onResume 调用 markStarted(),写入 STARTED
 * 3. 下次启动时调用 wasNativeCrashLastTime():
 *    - 如果上次状态是 STARTING(没到 STARTED),说明在 onCreate 之后 onResume 之前崩溃
 *    - native SIGSEGV 在 Java UncaughtExceptionHandler 抓不到,crash.log 不会写入
 *    - 这种情况下由 UI 显示 native 崩溃错误页
 *
 * 原理:Application.onCreate 必然执行(进程启动),onResume 在 Activity 完全启动后执行。
 * 如果进程在两者之间被 native 信号杀掉,SharedPreferences 不会被 markStarted 覆盖。
 */
object StartupTracker {

    private const val PREF_NAME = "startup_tracker"
    private const val KEY_STATE = "state"
    private const val STATE_IDLE = "idle"
    private const val STATE_STARTING = "starting"
    private const val STATE_STARTED = "started"

    private fun prefs(ctx: Context) =
        ctx.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    /** 在 Application.onCreate 调用,标记进程开始启动 */
    fun markStarting() {
        runCatching {
            prefs(TvFoxApp.getApp()).edit { putString(KEY_STATE, STATE_STARTING) }
        }
    }

    /** 在 Activity.onResume 调用,标记 Activity 已成功显示 */
    fun markStarted() {
        runCatching {
            prefs(TvFoxApp.getApp()).edit { putString(KEY_STATE, STATE_STARTED) }
        }
    }

    /**
     * 启动时检查:上次启动是否因 native 崩溃而中断。
     * 返回 true 表示上次启动在到达 Activity.onResume 之前就崩溃了
     * (Java 层 UncaughtExceptionHandler 抓不到,无 crash.log)。
     */
    fun wasNativeCrashLastTime(): Boolean {
        return runCatching {
            val state = prefs(TvFoxApp.getApp()).getString(KEY_STATE, STATE_IDLE)
            state == STATE_STARTING
        }.getOrDefault(false)
    }

    /** 重置状态(进入浏览器正常使用后调用) */
    fun reset() {
        runCatching {
            prefs(TvFoxApp.getApp()).edit { putString(KEY_STATE, STATE_STARTED) }
        }
    }
}
