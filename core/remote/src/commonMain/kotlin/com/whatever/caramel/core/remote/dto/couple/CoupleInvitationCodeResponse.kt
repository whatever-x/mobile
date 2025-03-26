package com.whatever.caramel.core.remote.dto.couple

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoupleInvitationCodeResponse(
    @SerialName("invitationCode") val invitationCode: String,
    @SerialName("expirationDateTime") val expirationDateTime: String
)
