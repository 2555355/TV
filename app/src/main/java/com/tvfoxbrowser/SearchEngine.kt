package com.tvfoxbrowser

/**
 * 搜索引擎与 URL 解析。
 * 输入可能是 URL,也可能是搜索关键词,需统一归一为可加载的 https URL。
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

    /**
     * 将用户输入归一为最终要加载的 URL。
     * - 空 -> 主页 about:home
     * - 看起来像 URL -> 自动补 https://
     * - 否则 -> 搜索引擎查询
     */
    fun normalize(input: String): String {
        val trimmed = input.trim()
        if (trimmed.isEmpty()) return "about:home"
        if (trimmed == "about:home") return trimmed

        // 带协议
        if (trimmed.startsWith("http://") || trimmed.startsWith("https://") ||
            trimmed.startsWith("about:") || trimmed.startsWith("file://")
        ) {
            return trimmed
        }

        // 看起来像域名: 含点、无空格、末段为字母
        val looksLikeUrl = trimmed.contains('.') &&
            !trimmed.contains(' ') &&
            trimmed.split('.').last().length in 2..24 &&
            trimmed.split('.').last().all { it.isLetterOrDigit() }

        return if (looksLikeUrl) {
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
