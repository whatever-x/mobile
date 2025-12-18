package com.whatever.caramel.core.datasource.remote.dto.user.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSettingRequest(
    @SerialName("notificationEnabled") val notificationEnabled: Boolean,
)
