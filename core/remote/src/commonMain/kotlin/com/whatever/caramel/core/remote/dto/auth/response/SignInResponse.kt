package com.whatever.caramel.core.remote.dto.auth.response

import com.whatever.caramel.core.remote.dto.auth.ServiceTokenDto
import com.whatever.caramel.core.remote.dto.user.UserStatusDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    @SerialName("serviceToken") val serviceToken: ServiceTokenDto,
    @SerialName("userStatus") val userStatus: UserStatusDto,
    @SerialName("nickname") val nickname: String?,
    @SerialName("birthDay") val birthDay: String?,
    @SerialName("coupleId") val coupleId: Long?,
    @SerialName("userId") val userId: Long,
)
