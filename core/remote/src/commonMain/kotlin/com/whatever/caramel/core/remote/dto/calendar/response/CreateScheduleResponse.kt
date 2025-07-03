package com.whatever.caramel.core.remote.dto.calendar.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateScheduleResponse(
    @SerialName("contentId")
    val contentId: Long,
    @SerialName("contentType")
    val contentType: String,
)
