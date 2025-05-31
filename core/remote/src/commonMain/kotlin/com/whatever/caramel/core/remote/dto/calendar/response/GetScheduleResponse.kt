package com.whatever.caramel.core.remote.dto.calendar.response

import com.whatever.caramel.core.remote.dto.tag.TagDetailResponse
import kotlinx.serialization.Serializable

@Serializable
data class GetScheduleResponse(
    val scheduleId: Long,
    val startDateTime: String,
    val endDateTime: String,
    val startDateTimezone: String,
    val endDateTimezone: String,
    val isCompleted: Boolean,
    val parentScheduleId: Long?,
    val title: String?,
    val description: String?,
    val tags: List<TagDetailResponse>,
)