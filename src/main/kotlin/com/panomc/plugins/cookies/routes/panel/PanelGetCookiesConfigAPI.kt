package com.panomc.plugins.cookies.routes.panel

import com.panomc.platform.annotation.Endpoint
import com.panomc.platform.api.config.PluginConfigManager
import com.panomc.platform.auth.AuthProvider
import com.panomc.platform.model.*
import com.panomc.plugins.cookies.CookiesPlugin
import com.panomc.plugins.cookies.config.CookiesConfig
import com.panomc.plugins.cookies.permission.ManageCookiesPermission
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext

import io.vertx.ext.web.validation.ValidationHandler
import io.vertx.ext.web.validation.builder.ValidationHandlerBuilder
import io.vertx.json.schema.SchemaRepository

@Endpoint
class PanelGetCookiesConfigAPI(
    private val plugin: CookiesPlugin,
) : PanelApi() {
    override val paths = listOf(Path("/api/panel/cookies/config", RouteType.GET))

    private val authProvider by lazy {
        plugin.applicationContext.getBean(AuthProvider::class.java)
    }

    private val configManager by lazy {
        plugin.pluginBeanContext.getBean(PluginConfigManager::class.java) as PluginConfigManager<CookiesConfig>
    }

    override fun getValidationHandler(schemaRepository: SchemaRepository): ValidationHandler =
        ValidationHandlerBuilder.create(schemaRepository)
            .build()

    override suspend fun handle(context: RoutingContext): Result {
        authProvider.requirePermission(ManageCookiesPermission(), context)

        return Successful(JsonObject.mapFrom(configManager.config).map)
    }
}
