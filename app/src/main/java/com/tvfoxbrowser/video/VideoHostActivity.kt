package com.tvfoxbrowser.video

import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.tvfoxbrowser.R
import java.io.IOException

/**
 * 内置视频播放 Activity(降级方案)。
 *
 * 适用场景:用户没装 MX Player / VLC。
 * 用系统 MediaPlayer + SurfaceView 播放。
 *
 * 局限:
 * - Android 5.1 的 MediaPlayer 不支持 m3u8(HLS),只能播 mp4
 * - 硬解能力取决于 ROM,可能卡顿
 *
 * 播放期间显示返回浮窗,遥控器可点击返回浏览器。
 */
class VideoHostActivity : Activity(), SurfaceHolder.Callback {

    companion object {
        const val EXTRA_URL = "video_url"
        const val EXTRA_TITLE = "video_title"
        private const val TAG = "VideoHost"
    }

    private var surfaceView: android.view.SurfaceView? = null
    private var statusView: TextView? = null
    private var mediaPlayer: MediaPlayer? = null
    private var videoUrl: String = ""
    private var isPrepared = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 全屏 + 横屏 + 亮屏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContentView(R.layout.video_host)

        videoUrl = intent.getStringExtra(EXTRA_URL).orEmpty()
        val title = intent.getStringExtra(EXTRA_TITLE)

        surfaceView = findViewById(R.id.surface_view)
        statusView = findViewById(R.id.tv_status)

        findViewById<View>(R.id.btn_back).setOnClickListener {
            finish()
        }

        if (videoUrl.isBlank()) {
            showError(getString(R.string.video_error, "URL 为空"))
            return
        }

        showStatus(getString(R.string.video_loading))

        surfaceView?.holder?.apply {
            addCallback(this@VideoHostActivity)
            // 5.1 上某些设备需要指定格式才显示画面
            setFormat(PixelFormat.RGBA_8888)
        }

        Log.i(TAG, "VideoHost created for: $videoUrl  title=$title")
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d(TAG, "Surface created, preparing MediaPlayer")
        prepareAndPlay(holder)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d(TAG, "Surface destroyed, releasing MediaPlayer")
        releasePlayer()
    }

    private fun prepareAndPlay(holder: SurfaceHolder) {
        releasePlayer()
        try {
            val mp = MediaPlayer()
            mp.setDisplay(holder)
            mp.setDataSource(this, Uri.parse(videoUrl))
            mp.setOnPreparedListener { player ->
                Log.i(TAG, "MediaPlayer prepared, starting playback")
                isPrepared = true
                hideStatus()
                player.start()
            }
            mp.setOnErrorListener { _, what, extra ->
                Log.e(TAG, "MediaPlayer error: what=$what extra=$extra")
                showError(getString(R.string.video_play_failed))
                true
            }
            mp.setOnCompletionListener {
                Log.d(TAG, "Playback completed")
                finish()
            }
            mp.isLooping = false
            // 5.1 上音频流类型必须显式设,否则可能没声音
            try {
                mp.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC)
            } catch (_: Throwable) {}
            mp.prepareAsync()
            mediaPlayer = mp
        } catch (e: IOException) {
            Log.e(TAG, "IOException preparing MediaPlayer", e)
            showError(getString(R.string.video_error, e.message ?: "IO 错误"))
        } catch (e: Throwable) {
            Log.e(TAG, "Failed to prepare MediaPlayer", e)
            showError(getString(R.string.video_error, e.message ?: "未知错误"))
        }
    }

    private fun releasePlayer() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) it.stop()
                it.release()
            }
        } catch (_: Throwable) {}
        mediaPlayer = null
        isPrepared = false
    }

    private fun showStatus(text: String) {
        runOnUiThread {
            statusView?.text = text
            statusView?.visibility = View.VISIBLE
        }
    }

    private fun hideStatus() {
        runOnUiThread {
            statusView?.visibility = View.GONE
        }
    }

    private fun showError(text: String) {
        runOnUiThread {
            statusView?.text = text
            statusView?.visibility = View.VISIBLE
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    @Deprecated("按返回键退出播放")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
