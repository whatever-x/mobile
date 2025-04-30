package com.whatever.caramel.core.remote.dto.couple

import com.whatever.caramel.core.remote.dto.user.GenderDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoupleUserInfoDto(
    @SerialName("id") val id: Long,
    @SerialName("nickname") val nickname: String,
    @SerialName("birthDate") val birthDate: String,
    @SerialName("gender") val gender: GenderDto,
)
