package com.panomc.plugins.cookies

import com.panomc.platform.api.PanoPlugin
import com.panomc.platform.api.config.PluginConfigManager
import com.panomc.plugins.cookies.config.CookiesConfig

class CookiesPlugin : PanoPlugin() {
    override suspend fun onStart() {
        logger.info("Starting Cookies Plugin...")

        // A stop / start in the same context keeps the registered manager: registering it again would fail.
        val beanFactory = pluginBeanContext.beanFactory
        if (!beanFactory.containsSingleton(PluginConfigManager::class.java.name)) {
            beanFactory.registerSingleton(PluginConfigManager::class.java.name, PluginConfigManager(this, CookiesConfig::class.java))
        }
    }

    override suspend fun onEnable() {
        logger.info("Enabled Cookies Plugin!")
    }

    override suspend fun onUninstall() {
        logger.info("Uninstalling Cookies Plugin...")


        // add some cleanup codes for your data used in plugin before uninstalling
    }
}
