package com.panomc.plugins.cookies.routes

import com.panomc.platform.annotation.Endpoint
import com.panomc.platform.api.config.PluginConfigManager
import com.panomc.platform.model.*
import com.panomc.plugins.cookies.CookiesPlugin
import com.panomc.plugins.cookies.config.CookiesConfig
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.validation.ValidationHandler
import com.panomc.platform.schema.dsl.ValidationHandlerBuilder
import io.vertx.json.schema.SchemaRepository
import com.panomc.platform.schema.EndpointDoc
import io.vertx.json.schema.common.dsl.Schemas.*

@Endpoint
class GetCookiesConfigAPI(
    private val plugin: CookiesPlugin,
) : Api() {
    override val paths = listOf(Path("/config", RouteType.GET))

    override val doc = EndpointDoc(
        summary = "The look and text of the cookie notice.",
        tag = "cookies",
        response = objectSchema()
            .requiredProperty("enabled", booleanSchema())
            .requiredProperty("message", stringSchema())
            .requiredProperty("buttonText", stringSchema())
            .requiredProperty("linkText", stringSchema())
            .requiredProperty("linkUrl", stringSchema())
            .requiredProperty("design", stringSchema())
            .requiredProperty("position", stringSchema())
            .requiredProperty("primaryColor", stringSchema())
            .requiredProperty("textColor", stringSchema())
            .requiredProperty("backgroundColor", stringSchema())
            .requiredProperty("useCustomContent", booleanSchema())
            .requiredProperty("customContent", stringSchema())
    )

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
