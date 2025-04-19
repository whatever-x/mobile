package com.whatever.caramel.core.remote.dto.couple

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoupleStartDateUpdateRequest(
    @SerialName("startDate") val startDate : String
)
