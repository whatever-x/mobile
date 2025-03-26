package com.whatever.caramel.core.remote.dto.couple

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoupleConnectResponse(
    @SerialName("coupleId") val coupleId : Long,
    @SerialName("startDate") val startDate : String,
    @SerialName("sharedMessage") val sharedMessage : String,
    @SerialName("myInfo") val myInfo : CoupleConnectedUserInfoResponse,
    @SerialName("partnerInfo") val partnerInfo : CoupleConnectedUserInfoResponse,
)

@Serializable
data class CoupleConnectedUserInfoResponse(
    @SerialName("id") val id : Long,
    @SerialName("nickname") val nickname : String,
    @SerialName("birthDate") val birthDate : String
)