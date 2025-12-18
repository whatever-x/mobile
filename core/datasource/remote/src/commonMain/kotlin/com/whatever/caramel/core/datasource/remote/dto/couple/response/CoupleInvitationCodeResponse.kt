package com.whatever.caramel.core.datasource.remote.dto.couple.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoupleInvitationCodeResponse(
    @SerialName("invitationCode") val invitationCode: String,
    @SerialName("expirationDateTime") val expirationDateTime: String,
)
