package com.whatever.caramel.core.domain.vo.calendar

data class ScheduleEditParameter(
    val selectedDate: String,
    val title: String?,
    val description: String?,
    val isCompleted: Boolean,
    val startDateTime: String,
    val startTimeZone: String,
    val endDateTime: String?,
    val endTimeZone: String?,
    val tagIds: List<Long>
) 