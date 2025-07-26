package com.whatever.caramel.core.remote.dto.calendar.request

import com.whatever.caramel.core.remote.dto.memo.ContentAsignee
import kotlinx.serialization.Serializable

@Serializable
data class UpdateScheduleRequest(
    val selectedDate: String,
    val title: String?,
    val description: String?,
    val isCompleted: Boolean,
    val startDateTime: String?,
    val startTimeZone: String?,
    val endDateTime: String?,
    val endTimeZone: String?,
    val tagIds: List<Long>,
    val contentAsignee: ContentAsignee,
)
