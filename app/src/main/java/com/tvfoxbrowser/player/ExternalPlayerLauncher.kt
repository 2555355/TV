package com.tvfoxbpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
importpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayerpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGESpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    privatepackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYERpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videopackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param titlepackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String,package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        ifpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String,package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE))package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByActionpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?,package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEWpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MXpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkgpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkgpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTHpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException)package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkgpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context,package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").applypackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decodepackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched bypackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "titlepackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl:package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates =package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolanpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: Stringpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDatapackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASKpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context,package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (epackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)",package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?):package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parsepackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(contextpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed",package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    privatepackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context,package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAGpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n"package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\npackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装")package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装") { _, _ ->
                    openStore(context, MXpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装") { _, _ ->
                    openStore(context, MX_PLAYER_FREE)
                }
                .setNegativeButton("取消", null)
                .setNeutralButton("package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装") { _, _ ->
                    openStore(context, MX_PLAYER_FREE)
                }
                .setNegativeButton("取消", null)
                .setNeutralButton("内置播放") { _, _ ->
                    //package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装") { _, _ ->
                    openStore(context, MX_PLAYER_FREE)
                }
                .setNegativeButton("取消", null)
                .setNeutralButton("内置播放") { _, _ ->
                    // 降级到内置,但需要再传一次package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装") { _, _ ->
                    openStore(context, MX_PLAYER_FREE)
                }
                .setNegativeButton("取消", null)
                .setNeutralButton("内置播放") { _, _ ->
                    // 降级到内置,但需要再传一次 URL,这里通过 Application 暂存
package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装") { _, _ ->
                    openStore(context, MX_PLAYER_FREE)
                }
                .setNegativeButton("取消", null)
                .setNeutralButton("内置播放") { _, _ ->
                    // 降级到内置,但需要再传一次 URL,这里通过 Application 暂存
                    // 实际不会走到这里(tryInternalPlayer 已成功就 return),只作兜底package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装") { _, _ ->
                    openStore(context, MX_PLAYER_FREE)
                }
                .setNegativeButton("取消", null)
                .setNeutralButton("内置播放") { _, _ ->
                    // 降级到内置,但需要再传一次 URL,这里通过 Application 暂存
                    // 实际不会走到这里(tryInternalPlayer 已成功就 return),只作兜底
                }
                .show()
        } catch (e: Exception) {
            // UIpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装") { _, _ ->
                    openStore(context, MX_PLAYER_FREE)
                }
                .setNegativeButton("取消", null)
                .setNeutralButton("内置播放") { _, _ ->
                    // 降级到内置,但需要再传一次 URL,这里通过 Application 暂存
                    // 实际不会走到这里(tryInternalPlayer 已成功就 return),只作兜底
                }
                .show()
        } catch (e: Exception) {
            // UI 上下文拿不到时退化为 Toast
package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装") { _, _ ->
                    openStore(context, MX_PLAYER_FREE)
                }
                .setNegativeButton("取消", null)
                .setNeutralButton("内置播放") { _, _ ->
                    // 降级到内置,但需要再传一次 URL,这里通过 Application 暂存
                    // 实际不会走到这里(tryInternalPlayer 已成功就 return),只作兜底
                }
                .show()
        } catch (e: Exception) {
            // UI 上下文拿不到时退化为 Toast
            Toast.makeText(context,
                "未找到视频播放器,建议安装 MX Player", Toast.LENGTH_LONG).show()
        }
    }

    private fun openStore(context: Context, pkg: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$pkg")).apply {
                addFlags(Intentpackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装") { _, _ ->
                    openStore(context, MX_PLAYER_FREE)
                }
                .setNegativeButton("取消", null)
                .setNeutralButton("内置播放") { _, _ ->
                    // 降级到内置,但需要再传一次 URL,这里通过 Application 暂存
                    // 实际不会走到这里(tryInternalPlayer 已成功就 return),只作兜底
                }
                .show()
        } catch (e: Exception) {
            // UI 上下文拿不到时退化为 Toast
            Toast.makeText(context,
                "未找到视频播放器,建议安装 MX Player", Toast.LENGTH_LONG).show()
        }
    }

    private fun openStore(context: Context, pkg: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$pkg")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // 没有应用商店,package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装") { _, _ ->
                    openStore(context, MX_PLAYER_FREE)
                }
                .setNegativeButton("取消", null)
                .setNeutralButton("内置播放") { _, _ ->
                    // 降级到内置,但需要再传一次 URL,这里通过 Application 暂存
                    // 实际不会走到这里(tryInternalPlayer 已成功就 return),只作兜底
                }
                .show()
        } catch (e: Exception) {
            // UI 上下文拿不到时退化为 Toast
            Toast.makeText(context,
                "未找到视频播放器,建议安装 MX Player", Toast.LENGTH_LONG).show()
        }
    }

    private fun openStore(context: Context, pkg: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$pkg")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // 没有应用商店,跳浏览器 Google Play 网页
            try {
                val intent = Intent(Intent.ACTION_VIEW,
package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装") { _, _ ->
                    openStore(context, MX_PLAYER_FREE)
                }
                .setNegativeButton("取消", null)
                .setNeutralButton("内置播放") { _, _ ->
                    // 降级到内置,但需要再传一次 URL,这里通过 Application 暂存
                    // 实际不会走到这里(tryInternalPlayer 已成功就 return),只作兜底
                }
                .show()
        } catch (e: Exception) {
            // UI 上下文拿不到时退化为 Toast
            Toast.makeText(context,
                "未找到视频播放器,建议安装 MX Player", Toast.LENGTH_LONG).show()
        }
    }

    private fun openStore(context: Context, pkg: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$pkg")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // 没有应用商店,跳浏览器 Google Play 网页
            try {
                val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/storepackage com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装") { _, _ ->
                    openStore(context, MX_PLAYER_FREE)
                }
                .setNegativeButton("取消", null)
                .setNeutralButton("内置播放") { _, _ ->
                    // 降级到内置,但需要再传一次 URL,这里通过 Application 暂存
                    // 实际不会走到这里(tryInternalPlayer 已成功就 return),只作兜底
                }
                .show()
        } catch (e: Exception) {
            // UI 上下文拿不到时退化为 Toast
            Toast.makeText(context,
                "未找到视频播放器,建议安装 MX Player", Toast.LENGTH_LONG).show()
        }
    }

    private fun openStore(context: Context, pkg: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$pkg")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // 没有应用商店,跳浏览器 Google Play 网页
            try {
                val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$pkg")).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            } catch (e2package com.tvfoxbrowser.player

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.tvfoxbrowser.TvFoxApp
import com.tvfoxbrowser.player.VideoHostActivity

/**
 * 外部播放器启动器。
 *
 * 设计:
 * 1. 优先调起 MX Player(免费版 com.mxtech.videoplayer.ad)硬解播放,最高清流畅
 * 2. MX Player 没装则尝试 VLC
 * 3. 都没装则用 App 内置的 VideoHostActivity(系统 MediaPlayer + SurfaceView)降级
 * 4. 都失败则弹框引导用户去应用商店安装 MX Player
 *
 * 兼容 Android 5.1+:
 * - 不用 Android 11+ 的 QUERY_ALL_PACKAGES,改用 try/catch 启动 Intent(更稳)
 * - MX Player 用其公开 Intent action,不必提前查询包名
 */
object ExternalPlayerLauncher {

    private const val TAG = "ExtPlayerLauncher"

    /** MX Player 免费版包名 */
    private const val MX_PLAYER_FREE = "com.mxtech.videoplayer.ad"
    /** MX Player Pro 版包名 */
    private const val MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"
    /** VLC for Android 包名 */
    private const val VLC = "org.videolan.vlc.betav7neon"

    /**
     * 启动外部播放器播放视频 URL。
     *
     * @param videoUrl 视频地址(m3u8/mp4/flv 等)
     * @param title    可选标题,MX Player 顶部会显示
     * @return true 表示已成功调起某个播放器
     */
    fun launch(context: Context, videoUrl: String, title: String? = null): Boolean {
        Log.i(TAG, "Launch external player for: $videoUrl")

        // 1. 优先 MX Player
        if (tryMxPlayer(context, videoUrl, title)) return true
        // 2. VLC
        if (tryVlc(context, videoUrl, title)) return true
        // 3. 内置降级播放器(系统 MediaPlayer)
        if (tryInternalPlayer(context, videoUrl, title)) return true
        // 4. 全部失败,引导安装
        showInstallPrompt(context)
        return false
    }

    /**
     * 尝试用 MX Player 启动。
     * MX Player 公开 Intent action: com.mxtech.intent.play.VIEW
     * 数据可以是 http(s) URL 或本地文件 URI。
     */
    private fun tryMxPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        // 免费版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_FREE)) return true
        // Pro 版
        if (launchByPackage(context, videoUrl, title, MX_PLAYER_PRO)) return true
        // 用 action 启动(不指定包名,让系统选择)
        return launchByAction(context, videoUrl, title)
    }

    private fun launchByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                // MX Player 专属:decode_mode 强制硬解
                putExtra("decode_mode", 1) // 1 = HW+
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched MX Player ($pkg) for $videoUrl")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            // 该包未安装,继续尝试下一个
            false
        } catch (e: SecurityException) {
            // Android 11+ 包可见性限制,catch 后继续
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player launch failed ($pkg)", e)
            false
        }
    }

    private fun launchByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("com.mxtech.intent.play.VIEW").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                putExtra("decode_mode", 1)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched by action com.mxtech.intent.play.VIEW")
            Toast.makeText(context, "已调起 MX Player", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "MX Player action launch failed", e)
            false
        }
    }

    /**
     * 尝试用 VLC 启动。
     * VLC 公开 Intent action: org.videolan.vlc.intent.action.START
     * VLC 通过 extra "title" 传标题。
     */
    private fun tryVlc(context: Context, videoUrl: String, title: String?): Boolean {
        // 优先用包名
        val candidates = arrayOf(
            VLC,
            "org.videolan.vlc",
            "org.videolan.vlc.betav7neon"
        )
        for (pkg in candidates) {
            if (launchVlcByPackage(context, videoUrl, title, pkg)) return true
        }
        // 用 action
        return launchVlcByAction(context, videoUrl, title)
    }

    private fun launchVlcByPackage(
        context: Context, videoUrl: String, title: String?, pkg: String
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(pkg)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC ($pkg)")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC launch failed ($pkg)", e)
            false
        }
    }

    private fun launchVlcByAction(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent("org.videolan.vlc.intent.action.START").apply {
                setDataAndType(Uri.parse(videoUrl), guessMime(videoUrl))
                putExtra("title", title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched VLC by action")
            Toast.makeText(context, "已调起 VLC", Toast.LENGTH_SHORT).show()
            true
        } catch (e: ActivityNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "VLC action launch failed", e)
            false
        }
    }

    /**
     * 内置降级播放器:VideoHostActivity(系统 MediaPlayer + SurfaceView)。
     * 流畅度不如 MX Player,但聊胜于无。
     */
    private fun tryInternalPlayer(context: Context, videoUrl: String, title: String?): Boolean {
        return try {
            val intent = Intent(context, VideoHostActivity::class.java).apply {
                putExtra(VideoHostActivity.EXTRA_URL, videoUrl)
                putExtra(VideoHostActivity.EXTRA_TITLE, title ?: "")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.i(TAG, "Launched internal VideoHostActivity")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Internal player launch failed", e)
            false
        }
    }

    /** 全部失败时,弹框引导安装 MX Player */
    private fun showInstallPrompt(context: Context) {
        try {
            AlertDialog.Builder(context)
                .setTitle("未找到视频播放器")
                .setMessage("检测到视频地址,但 MX Player / VLC 都未安装。\n\n" +
                    "建议安装 MX Player 以获得最佳播放体验(支持硬解高清)。\n\n" +
                    "点「去安装」跳转应用商店,或点「内置播放」用浏览器自带播放器(可能卡顿)。")
                .setPositiveButton("去安装") { _, _ ->
                    openStore(context, MX_PLAYER_FREE)
                }
                .setNegativeButton("取消", null)
                .setNeutralButton("内置播放") { _, _ ->
                    // 降级到内置,但需要再传一次 URL,这里通过 Application 暂存
                    // 实际不会走到这里(tryInternalPlayer 已成功就 return),只作兜底
                }
                .show()
        } catch (e: Exception) {
            // UI 上下文拿不到时退化为 Toast
            Toast.makeText(context,
                "未找到视频播放器,建议安装 MX Player", Toast.LENGTH_LONG).show()
        }
    }

    private fun openStore(context: Context, pkg: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$pkg")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // 没有应用商店,跳浏览器 Google Play 网页
            try {
                val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$pkg")).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            } catch (e2: Exception) {
                Toast.makeText(context, "无法打开应用商店", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**