package com.whatever.caramel.core.remote.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class LoginPlatformDto {
    @SerialName("KAKAO") KAKAO,
    @SerialName("APPLE") APPLE,
    ;
}