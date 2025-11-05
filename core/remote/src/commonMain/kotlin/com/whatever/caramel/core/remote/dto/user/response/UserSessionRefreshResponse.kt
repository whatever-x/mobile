package com.whatever.caramel.core.remote.dto.user.response

import com.whatever.caramel.core.remote.dto.auth.ServiceTokenDto
import com.whatever.caramel.core.remote.dto.user.UserStatusDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSessionRefreshResponse(
    @SerialName("userId") val userId: Long,
    @SerialName("userStatus") val userStatus: UserStatusDto,
    @SerialName("serviceToken") val serviceToken: ServiceTokenDto,
)