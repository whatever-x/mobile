package com.whatever.caramel.core.domain.vo.content.schedule

import com.whatever.caramel.core.domain.vo.content.ContentAssignee

data class CreateScheduleParameter(
    val title: String?,
    val description: String?,
    val isCompleted: Boolean,
    val startDateTime: String,
    val startTimeZone: String,
    val endDateTime: String?,
    val endTimeZone: String?,
    val tagIds: List<Long>?,
    val contentAssignee: ContentAssignee,
)
