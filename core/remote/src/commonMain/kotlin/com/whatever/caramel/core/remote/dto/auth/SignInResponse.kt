package com.whatever.caramel.core.remote.dto.auth

import com.whatever.caramel.core.remote.dto.user.UserStatusDto
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    @SerialName("serviceToken") val serviceToken: ServiceToken,
    @SerialName("userStatus") val userStatus: UserStatusDto,
    @SerialName("nickname") val nickname: String?,
    @SerialName("birthDay") val birthDay: String?,
    @SerialName("coupleId") val coupleId: Long?,
)