package com.whatever.caramel.core.remote.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditUserProfileRequest(
    @SerialName("nickname") val nickname : String,
    @SerialName("birthday") val birthday : String
)
