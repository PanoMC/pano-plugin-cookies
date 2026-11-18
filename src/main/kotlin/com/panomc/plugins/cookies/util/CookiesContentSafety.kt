package com.panomc.plugins.cookies.util

import io.vertx.core.json.JsonObject
import java.net.URI

/**
 * The link only takes http(s) URLs or paths on this site, and the colours only take plain colour values (they are
 * written into style attributes). The custom HTML is the admin's own content and is shown as written.
 */
object CookiesContentSafety {
    private val COLOR_FIELDS = listOf("primaryColor", "textColor", "backgroundColor")

    private val COLOR_PATTERN = Regex(
        "^(#[0-9a-fA-F]{3,8}|(rgb|rgba|hsl|hsla)\\([0-9.,%\\s]+\\)|[a-zA-Z]{1,32})$"
    )

    fun isSafeLinkUrl(url: String?): Boolean {
        if (url.isNullOrEmpty()) {
            return true
        }

        if (url.any { it.isISOControl() } || url != url.trim()) {
            return false
        }

        if (url.startsWith("/") && !url.startsWith("//") && !url.startsWith("/\\")) {
            return true
        }

        val uri = try {
            URI(url)
        } catch (_: Exception) {
            return false
        }

        val scheme = uri.scheme?.lowercase() ?: return false

        return (scheme == "http" || scheme == "https") && !uri.host.isNullOrBlank()
    }

    fun isSafeColor(color: String?): Boolean = color.isNullOrEmpty() || COLOR_PATTERN.matches(color)

    /** True when every field of an update body is acceptable. */
    fun isValidUpdate(body: JsonObject): Boolean {
        if (!isSafeLinkUrl(body.getValue("linkUrl") as? String)) {
            return false
        }

        return COLOR_FIELDS.all { isSafeColor(body.getValue(it) as? String) }
    }

    /** Cleans a config before it is sent to visitors, also covering values saved before these checks existed. */
    fun toPublic(config: JsonObject): JsonObject {
        if (!isSafeLinkUrl(config.getString("linkUrl"))) {
            config.put("linkUrl", "")
        }

        COLOR_FIELDS.forEach { field ->
            if (!isSafeColor(config.getString(field))) {
                config.put(field, "")
            }
        }

        return config
    }
}
