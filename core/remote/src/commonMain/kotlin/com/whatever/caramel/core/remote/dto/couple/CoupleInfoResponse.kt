package com.whatever.caramel.core.remote.dto.couple

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoupleInfoResponse(
    @SerialName("coupleId") val coupleId: Long,
    @SerialName("startDate") val startDate: String?,
    @SerialName("sharedMessage") val sharedMessage: String?,
    @SerialName("myInfo") val myInfo: CoupleUserInfoResponse,
    @SerialName("partnerInfo") val partnerInfo: CoupleUserInfoResponse
)

@Serializable
data class CoupleUserInfoResponse(
    @SerialName("id") val id: Long,
    @SerialName("nickname") val nickname: String,
    @SerialName("birthDate") val birthDate: String,
    @SerialName("gender") val gender: String,
)