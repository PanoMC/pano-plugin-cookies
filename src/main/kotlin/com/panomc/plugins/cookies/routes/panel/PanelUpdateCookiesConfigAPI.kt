package com.panomc.plugins.cookies.routes.panel

import com.panomc.platform.annotation.Endpoint
import com.panomc.platform.api.config.PluginConfigManager
import com.panomc.platform.auth.AuthProvider
import com.panomc.platform.db.DatabaseManager
import com.panomc.platform.model.*
import com.panomc.plugins.cookies.CookiesPlugin
import com.panomc.plugins.cookies.config.CookiesConfig
import com.panomc.plugins.cookies.log.UpdatedCookiesConfigLog
import com.panomc.plugins.cookies.permission.ManageCookiesPermission
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.validation.ValidationHandler
import io.vertx.ext.web.validation.builder.Bodies
import io.vertx.ext.web.validation.builder.ValidationHandlerBuilder
import io.vertx.json.schema.SchemaRepository
import io.vertx.json.schema.common.dsl.Schemas.*

@Endpoint
class PanelUpdateCookiesConfigAPI(
    private val plugin: CookiesPlugin,
) : PanelApi() {
    override val paths = listOf(Path("/api/panel/cookies/config", RouteType.PUT))

    private val authProvider by lazy {
        plugin.applicationContext.getBean(AuthProvider::class.java)
    }

    private val databaseManager by lazy {
        plugin.applicationContext.getBean(DatabaseManager::class.java)
    }

    private val configManager by lazy {
        plugin.pluginBeanContext.getBean(PluginConfigManager::class.java) as PluginConfigManager<CookiesConfig>
    }

    override fun getValidationHandler(schemaRepository: SchemaRepository): ValidationHandler =
        ValidationHandlerBuilder.create(schemaRepository)
            .body(
                Bodies.json(
                    objectSchema()
                        .optionalProperty("enabled", booleanSchema())
                        .optionalProperty("message", stringSchema())
                        .optionalProperty("buttonText", stringSchema())
                        .optionalProperty("linkText", stringSchema())
                        .optionalProperty("linkUrl", stringSchema())
                        .optionalProperty("design", stringSchema())
                        .optionalProperty("position", stringSchema())
                        .optionalProperty("primaryColor", stringSchema())
                        .optionalProperty("textColor", stringSchema())
                        .optionalProperty("backgroundColor", stringSchema())
                        .optionalProperty("useCustomContent", booleanSchema())
                        .optionalProperty("customContent", stringSchema())
                )
            )
            .build()

    override suspend fun handle(context: RoutingContext): Result {
        authProvider.requirePermission(ManageCookiesPermission(), context)

        val body = context.body().asJsonObject()
        configManager.saveConfig(body)

        val userId = authProvider.getUserIdFromRoutingContext(context)
        val sqlClient = databaseManager.getSqlClient()
        val username = databaseManager.userDao.getUsernameFromUserId(userId, sqlClient) ?: "Unknown"

        databaseManager.panelActivityLogDao.add(
            UpdatedCookiesConfigLog(userId, username, plugin.pluginId),
            sqlClient
        )

        return Successful()
    }
}
