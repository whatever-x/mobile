package com.whatever.caramel.core.remote.dto.user.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileRequest(
    @SerialName("nickname") val nickname: String,
    @SerialName("birthday") val birthday: String,
    @SerialName("gender") val gender: String,
    @SerialName("agreementServiceTerms") val agreementServiceTerms: Boolean,
    @SerialName("agreementPrivatePolicy") val agreementPrivatePolicy: Boolean,
)
