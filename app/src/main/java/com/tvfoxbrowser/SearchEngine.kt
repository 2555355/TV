package com.tvfoxbrowser

import java.util.regex.Pattern

/**
 * 搜索引擎与 URL 解析。
 * 输入可能是 URL,也可能是搜索关键词,需统一归一为可加载的 URL。
 */
object SearchEngine {

    data class Engine(
        val id: String,
        val nameRes: Int,
        val queryUrl: String // {query} 占位
    )

    val ENGINES: List<Engine> = listOf(
        Engine("bing", R.string.engine_bing, "https://www.bing.com/search?q={query}"),
        Engine("baidu", R.string.engine_baidu, "https://www.baidu.com/s?wd={query}"),
        Engine("google", R.string.engine_google, "https://www.google.com/search?q={query}"),
        Engine("duckduckgo", R.string.engine_duckduckgo, "https://duckduckgo.com/?q={query}")
    )

    fun engineById(id: String): Engine =
        ENGINES.firstOrNull { it.id == id } ?: ENGINES.first()

    // IPv4 地址(可选端口),如 192.168.1.100 / 127.0.0.1:8080 / 10.0.0.1:8000
    private val IPV4_RE: Pattern = Pattern.compile(
        """^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})(:\d{1,5})?$"""
    )

    // localhost / 127.0.0.1 / [::1] 等本地回环
    private val LOCALHOST_RE: Pattern = Pattern.compile(
        """^(localhost|\[?::1\]?)(:\d{1,5})?$""", Pattern.CASE_INSENSITIVE
    )

    /**
     * 将用户输入归一为最终要加载的 URL。
     * - 空 -> 主页 about:home
     * - 带协议(http/https/file/about) -> 原样返回
     * - 看起来是 IP 或 localhost -> 自动补 http://
     *   (本地服务通常没装 SSL,补 https:// 会因证书错误打不开)
     * - 看起来是域名 -> 自动补 https://
     * - 否则 -> 搜索引擎查询
     */
    fun normalize(input: String): String {
        val trimmed = input.trim()
        if (trimmed.isEmpty()) return "about:home"
        if (trimmed == "about:home") return trimmed

        // 已带协议
        if (trimmed.startsWith("http://") || trimmed.startsWith("https://") ||
            trimmed.startsWith("about:") || trimmed.startsWith("file://")
        ) {
            return trimmed
        }

        // localhost / [::1] -> http://
        if (LOCALHOST_RE.matcher(trimmed).matches()) {
            return "http://$trimmed"
        }

        // IPv4 -> http:// (本地局域网服务通常没 SSL)
        if (IPV4_RE.matcher(trimmed).matches()) {
            return "http://$trimmed"
        }

        // 域名: 含点、无空格、末段为字母(避免把 "192.168.1.100:8080" 误判为域名)
        // 末段需为字母,排除纯数字末段(那是 IP)
        val parts = trimmed.split('.')
        val looksLikeDomain = parts.size >= 2 &&
            !trimmed.contains(' ') &&
            parts.last().isNotEmpty() &&
            parts.last().all { it.isLetterOrDigit() } &&
            parts.last().any { it.isLetter() }

        return if (looksLikeDomain) {
            "https://$trimmed"
        } else {
            val engine = engineById(SettingsManager.get().searchEngineId)
            engine.queryUrl.replace("{query}", java.net.URLEncoder.encode(trimmed, "UTF-8"))
        }
    }

    /** 是否为搜索引擎结果页(用于历史记录去重/特殊处理) */
    fun isSearchUrl(url: String): Boolean {
        return ENGINES.any { url.startsWith(it.queryUrl.substringBefore("{query}")) }
    }
}
