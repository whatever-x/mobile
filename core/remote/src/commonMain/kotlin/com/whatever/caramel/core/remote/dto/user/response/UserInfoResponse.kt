package com.whatever.caramel.core.remote.dto.user.response

import com.whatever.caramel.core.remote.dto.auth.LoginPlatformDto
import com.whatever.caramel.core.remote.dto.user.GenderDto
import com.whatever.caramel.core.remote.dto.user.UserStatusDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    @SerialName("id") val id: Long,
    @SerialName("email") val email: String?,
    @SerialName("birthDate") val birthDate: String?,
    @SerialName("signInPlatform") val signInPlatform: LoginPlatformDto,
    @SerialName("nickname") val nickname: String?,
    @SerialName("gender") val gender: GenderDto?,
    @SerialName("userStatus") val userStatus: UserStatusDto,
)
