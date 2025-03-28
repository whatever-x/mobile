package com.whatever.caramel.core.remote.dto.couple

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoupleConnectResponse(
    @SerialName("coupleId") val coupleId : Long,
    @SerialName("startDate") val startDate : String,
    @SerialName("sharedMessage") val sharedMessage : String,
    @SerialName("myInfo") val myInfo : CoupleConnectUserResponse,
    @SerialName("partnerInfo") val partnerInfo : CoupleConnectUserResponse
)

@Serializable
data class CoupleConnectUserResponse(
    @SerialName("id") val id : Long,
    @SerialName("nickname") val nickname : String,
    @SerialName("birthDate") val birthDate : String
)
