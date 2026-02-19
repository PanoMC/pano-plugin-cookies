package com.panomc.plugins.cookies.log

import com.panomc.platform.db.model.PluginActivityLog
import io.vertx.core.json.JsonObject

class UpdatedCookiesConfigLog(
    userId: Long,
    username: String,
    pluginId: String,
) : PluginActivityLog(
    userId = userId,
    pluginId = pluginId,
    details = JsonObject().put("username", username)
)
