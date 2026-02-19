package com.panomc.plugins.cookies.config

import com.panomc.platform.api.config.PluginConfig

class CookiesConfig(
    val enabled: Boolean = true,
    val message: String = "We use cookies to ensure you get the best experience on our website.",
    val buttonText: String = "Got it!",
    val linkText: String = "Learn more",
    val linkUrl: String = "https://www.cookiesandyou.com/",
    val design: CookiesDesign = CookiesDesign.BAR,
    val position: CookiesPosition = CookiesPosition.BOTTOM,
    val primaryColor: String = "#007bff",
    val textColor: String = "#ffffff",
    val backgroundColor: String = "#343a40",
    val useCustomContent: Boolean = false,
    val customContent: String = "",
    version: Int = 1
) : PluginConfig(version)
