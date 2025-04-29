package com.whatever.caramel.core.remote.dto.couple.response

import com.whatever.caramel.core.remote.dto.user.response.GenderDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoupleDetailResponse(
    @SerialName("coupleId") val coupleId: Long,
    @SerialName("startDate") val startDate: String?,
    @SerialName("sharedMessage") val sharedMessage: String?,
    @SerialName("status") val status: CoupleStatusDto,
    @SerialName("myInfo") val myInfo: CoupleUserInfoDto,
    @SerialName("partnerInfo") val partnerInfo: CoupleUserInfoDto
)

@Serializable
data class CoupleBasicResponse(
    @SerialName("coupleId") val coupleId: Long,
    @SerialName("startDate") val startDate: String?,
    @SerialName("sharedMessage") val sharedMessage: String?,
    @SerialName("status") val status: CoupleStatusDto,
)

@Serializable
data class CoupleUserInfoDto(
    @SerialName("id") val id: Long,
    @SerialName("nickname") val nickname: String,
    @SerialName("birthDate") val birthDate: String,
    @SerialName("gender") val gender: GenderDto,
)

@Serializable
enum class CoupleStatusDto {
    @SerialName("ACTIVE") ACTIVE,
    @SerialName("INACTIVE") INACTIVE,
    ;
}
