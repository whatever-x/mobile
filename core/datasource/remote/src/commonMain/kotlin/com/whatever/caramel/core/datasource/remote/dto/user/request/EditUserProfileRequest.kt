package com.whatever.caramel.core.datasource.remote.dto.user.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditUserProfileRequest(
    @SerialName("nickname") val nickname: String?,
    @SerialName("birthday") val birthday: String?,
)
