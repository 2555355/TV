package com.tvfoxbrowser.video

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.R

/**
 * 外部播放器启动器:把视频 URL 交给 MX Player / VLC 等外部硬解播放器。
 *
 * 为什么需要它:
 * 海尔 HRA920L (Android 5.1, WebView 39) 的系统 WebView 内核太老,
 * 不支持 MSE (Media Source Extensions),B 站现代 H5 播放器无法工作。
 * 但 MX Player / VLC 自带完整解码器,能直接播 m3u8/mp4/flv。
 *
 * 策略(按优先级):
 * 1. MX Player Pro (com.mxtech.videoplayer.pro)
 * 2. MX Player Free (com.mxtech.videoplayer.ad)
 * 3. VLC (org.videolan.vlc)
 * 4. 系统默认视频 Intent (让用户选)
 * 5. 内置 VideoHostActivity (MediaPlayer + SurfaceView,降级)
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExternalPlayer"

    // 候选播放器(按优先级排序)
    private val MX_PLAYER_PACKAGES = listOf(
        "com.mxtech.videoplayer.pro",  // MX Player Pro
        "com.mxtech.videoplayer.ad"    // MX Player 免费版
    )
    private const val VLC_PACKAGE = "org.videolan.vlc"

    /**
     * 启动外部播放器播放视频。
     *
     * @param activity 调用方 Activity(用于 startActivity 和显示 Toast)
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,传给播放器显示
     * @return true=成功启动外部播放器; false=未找到任何外部播放器,已自动降级到内置播放器
     */
    fun launch(activity: Activity, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launching external player for: $videoUrl")

        // 1. 优先 MX Player(硬解能力最强,遥控器适配最好)
        for (pkg in MX_PLAYER_PACKAGES) {
            if (isAppInstalled(activity, pkg)) {
                if (launchMxPlayer(activity, videoUrl, title, pkg)) return true
            }
        }

        // 2. VLC(支持协议最全,流媒体兼容好)
        if (isAppInstalled(activity, VLC_PACKAGE)) {
            if (launchVlc(activity, videoUrl, title)) return true
        }

        // 3. 系统默认视频 Intent(让用户选其他已装播放器)
        if (launchSystemDefault(activity, videoUrl, title)) return true

        // 4. 降级:内置 MediaPlayer 播放
        Log.w(TAG, "No external player installed, falling back to built-in MediaPlayer")
        Toast.makeText(
            activity,
            activity.getString(R.string.video_no_external_player_hint),
            Toast.LENGTH_LONG
        ).show()
        return launchBuiltIn(activity, videoUrl, title)
    }

    /** 启动 MX Player */
    private fun launchMxPlayer(
        activity: Activity, videoUrl: String, title: String?, pkg: String
    ): Boolean = try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.parse(videoUrl), guessMimeType(videoUrl))
            setPackage(pkg)
            // MX Player 扩展 extra:标题
            putExtra("title", title ?: "")
            // MX Player 扩展 extra:是否自动开始播放
            putExtra("start_with_playback", true)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        activity.startActivity(intent)
        Log.i(TAG, "Launched MX Player: $pkg")
        true
    } catch (t: Throwable) {
        Log.w(TAG, "MX Player launch failed: $pkg", t)
        false
    }

    /** 启动 VLC */
    private fun launchVlc(activity: Activity, videoUrl: String, title: String?): Boolean = try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.parse(videoUrl), guessMimeType(videoUrl))
            setPackage(VLC_PACKAGE)
            putExtra("title", title ?: "")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        activity.startActivity(intent)
        Log.i(TAG, "Launched VLC")
        true
    } catch (t: Throwable) {
        Log.w(TAG, "VLC launch failed", t)
        false
    }

    /** 启动系统默认视频 Intent(让用户选其他播放器) */
    private fun launchSystemDefault(
        activity: Activity, videoUrl: String, title: String?
    ): Boolean = try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.parse(videoUrl), guessMimeType(videoUrl))
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        // 必须确认有 App 能接收,否则 startActivity 会抛 ActivityNotFoundException
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(Intent.createChooser(intent, title ?: "选择播放器"))
            Log.i(TAG, "Launched system default video player")
            true
        } else {
            false
        }
    } catch (t: Throwable) {
        Log.w(TAG, "System default launch failed", t)
        false
    }

    /** 降级:启动内置 VideoHostActivity(MediaPlayer + SurfaceView) */
    private fun launchBuiltIn(activity: Activity, videoUrl: String, title: String?): Boolean = try {
        val intent = Intent(activity, VideoHostActivity::class.java).apply {
            putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
            putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        activity.startActivity(intent)
        Log.i(TAG, "Launched built-in VideoHostActivity")
        true
    } catch (t: Throwable) {
        Log.e(TAG, "Built-in player launch failed", t)
        Toast.makeText(activity, "无法播放视频: ${t.message}", Toast.LENGTH_LONG).show()
        false
    }

    /** 根据扩展名猜测 MIME 类型 */
    private fun guessMimeType(url: String): String {
        val lower = url.substringBefore('?').lowercase()
        return when {
            lower.endsWith(".m3u8") -> "application/x-mpegURL"
            lower.endsWith(".mp4") -> "video/mp4"
            lower.endsWith(".m4v") -> "video/x-m4v"
            lower.endsWith(".mkv") -> "video/x-matroska"
            lower.endsWith(".flv") -> "video/x-flv"
            lower.endsWith(".webm") -> "video/webm"
            lower.endsWith(".avi") -> "video/x-msvideo"
            lower.endsWith(".ts") -> "video/mp2t"
            lower.endsWith(".mov") -> "video/quicktime"
            lower.endsWith(".3gp") -> "video/3gpp"
            else -> "video/*"  // 兜底,让播放器自己判断
        }
    }

    /** 检查 App 是否安装(Android 11+ 需 QUERY_ALL_PACKAGES,5.1 无限制) */
    private fun isAppInstalled(context: Context, pkg: String): Boolean = try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getPackageInfo(pkg, PackageManager.PackageInfoFlags.of(0)) != null
        } else {
            @Suppress("DEPRECATION")
            context.packageManager.getPackageInfo(pkg, 0) != null
        }
    } catch (_: PackageManager.NameNotFoundException) {
        false
    } catch (t: Throwable) {
        Log.w(TAG, "isAppInstalled check failed for $pkg", t)
        false
    }
}
