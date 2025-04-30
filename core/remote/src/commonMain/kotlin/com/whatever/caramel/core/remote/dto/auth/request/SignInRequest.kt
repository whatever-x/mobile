package com.whatever.caramel.core.remote.dto.auth.request

import com.whatever.caramel.core.remote.dto.auth.LoginPlatformDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    @SerialName("loginPlatform") val loginPlatform: LoginPlatformDto,
    @SerialName("idToken") val idToken: String,
)
