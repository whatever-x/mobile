package com.whatever.caramel.core.domain.vo.calendar

import com.whatever.caramel.core.domain.vo.content.ContentRole

data class ScheduleParameter(
    val title: String?,
    val description: String?,
    val isCompleted: Boolean,
    val startDateTime: String,
    val startTimeZone: String,
    val endDateTime: String?,
    val endTimeZone: String?,
    val tagIds: List<Long>?,
    val contentRole: ContentRole,
)
