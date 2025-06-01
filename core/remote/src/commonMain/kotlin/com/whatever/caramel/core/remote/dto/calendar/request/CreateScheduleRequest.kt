package com.whatever.caramel.core.remote.dto.calendar.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateScheduleRequest(
    @SerialName("title")
    val title: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("isCompleted")
    val isCompleted: Boolean,
    @SerialName("startDateTime")
    val startDateTime: String,
    @SerialName("startTimeZone")
    val startTimeZone: String,
    @SerialName("endDateTime")
    val endDateTime: String?,
    @SerialName("endTimeZone")
    val endTimeZone: String?,
    @SerialName("tagIds")
    val tagIds: List<Long>?
) 