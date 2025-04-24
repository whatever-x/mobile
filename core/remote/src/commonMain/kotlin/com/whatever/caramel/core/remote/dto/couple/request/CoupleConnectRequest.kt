package com.whatever.caramel.core.remote.dto.couple.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoupleConnectRequest(
    @SerialName("invitationCode") val invitationCode: String
)
