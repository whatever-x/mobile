package com.whatever.caramel.core.remote.dto.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CalendarDetailResponse(
    @SerialName("calendarResult") val calendarResult: CalendarApiResult,
)

@Serializable
data class CalendarApiResult(
    @SerialName("scheduleList") val scheduleList: List<ScheduleApiResult>,
)

@Serializable
data class ScheduleApiResult(
    @SerialName("scheduleId") val scheduleId: Long,
    @SerialName("startDateTime") val startDateTime: String,
    @SerialName("endDateTime") val endDateTime: String,
    @SerialName("startDateTimezone") val startDateTimezone: String,
    @SerialName("endDateTimezone") val endDateTimezone: String,
    @SerialName("isCompleted") val isCompleted: Boolean,
    @SerialName("parentScheduleId") val parentScheduleId: Long?,
    @SerialName("title") val title: String?,
    @SerialName("description") val description: String?,
)
