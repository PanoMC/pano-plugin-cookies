package com.panomc.plugins.cookies

import com.panomc.platform.api.PanoPlugin
import com.panomc.platform.api.config.PluginConfigManager
import com.panomc.plugins.cookies.config.CookiesConfig

class CookiesPlugin : PanoPlugin() {
    override suspend fun onStart() {
        logger.info("Starting Cookies Plugin...")

        val configManager = PluginConfigManager(this, CookiesConfig::class.java)
        pluginBeanContext.beanFactory.registerSingleton(PluginConfigManager::class.java.name, configManager)
    }

    override suspend fun onEnable() {
        logger.info("Enabled Cookies Plugin!")
    }

    override suspend fun onUninstall() {
        logger.info("Uninstalling Cookies Plugin...")


        // add some cleanup codes for your data used in plugin before uninstalling
    }
}
