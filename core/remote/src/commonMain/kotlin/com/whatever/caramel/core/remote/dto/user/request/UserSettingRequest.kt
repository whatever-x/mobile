package com.whatever.caramel.core.remote.dto.user.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSettingRequest(
    @SerialName("notificationEnabled") val notificationEnabled : Boolean
)
