package com.whatever.caramel.core.datasource.remote.dto.firebase.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FcmTokenRequest(
    @SerialName("token") val token: String,
)
