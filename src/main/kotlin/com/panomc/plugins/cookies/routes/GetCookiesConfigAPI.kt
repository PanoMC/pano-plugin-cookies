package com.panomc.plugins.cookies.routes

import com.panomc.platform.annotation.Endpoint
import com.panomc.platform.api.config.PluginConfigManager
import com.panomc.platform.model.*
import com.panomc.plugins.cookies.CookiesPlugin
import com.panomc.plugins.cookies.config.CookiesConfig
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.validation.ValidationHandler
import io.vertx.ext.web.validation.builder.ValidationHandlerBuilder
import io.vertx.json.schema.SchemaRepository

@Endpoint
class GetCookiesConfigAPI(
    private val plugin: CookiesPlugin,
) : Api() {
    override val paths = listOf(Path("/api/cookies/config", RouteType.GET))

    private val configManager by lazy {
        plugin.pluginBeanContext.getBean(PluginConfigManager::class.java) as PluginConfigManager<CookiesConfig>
    }

    override fun getValidationHandler(schemaRepository: SchemaRepository): ValidationHandler =
        ValidationHandlerBuilder.create(schemaRepository)
            .build()


    override suspend fun handle(context: RoutingContext): Result {
        return Successful(JsonObject.mapFrom(configManager.config).map)
    }
}
