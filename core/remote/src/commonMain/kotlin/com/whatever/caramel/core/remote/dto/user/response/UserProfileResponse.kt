package com.whatever.caramel.core.remote.dto.user.response

import com.whatever.caramel.core.remote.dto.user.UserStatusDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    @SerialName("id") val id: Long,
    @SerialName("nickname") val nickname: String,
    @SerialName("userStatus") val userStatus: UserStatusDto,
)
