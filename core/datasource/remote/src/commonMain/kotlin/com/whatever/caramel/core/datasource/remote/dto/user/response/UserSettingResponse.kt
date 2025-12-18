package com.whatever.caramel.core.datasource.remote.dto.user.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSettingResponse(
    @SerialName("notificationEnabled") val notificationEnabled: Boolean,
)
