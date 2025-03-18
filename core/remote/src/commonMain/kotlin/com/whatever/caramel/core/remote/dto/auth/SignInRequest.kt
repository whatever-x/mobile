package com.whatever.caramel.core.remote.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    @SerialName("loginPlatform") val loginPlatform: LoginPlatform,
    @SerialName("idToken") val idToken: String,
)

@Serializable
enum class LoginPlatform {
    @SerialName("KAKAO") KAKAO,
    @SerialName("APPLE") APPLE,
    ;
}