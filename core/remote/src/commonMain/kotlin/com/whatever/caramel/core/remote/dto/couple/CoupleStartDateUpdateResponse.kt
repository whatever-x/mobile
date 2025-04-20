package com.whatever.caramel.core.remote.dto.couple

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoupleStartDateUpdateResponse(
    @SerialName("coupleId") val coupleId: Long,
    @SerialName("startDate") val startDate: String,
    @SerialName("sharedMessage") val sharedMessage: String?,
)
