package com.whatever.caramel.core.remote.dto.auth

import com.whatever.caramel.core.remote.dto.user.UserStatus
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    @SerialName("serviceToken") val serviceToken: ServiceToken,
    @SerialName("userStatus") val userStatus: UserStatus,
    @SerialName("nickname") val nickname: String?,
    @SerialName("birthDay") val birthDay: LocalDate?,
    @SerialName("coupleId") val coupleId: Long?,
)