package com.whatever.caramel.core.domain.params.content.schdule

import com.whatever.caramel.core.domain.vo.content.ContentAssignee

data class ScheduleParameter(
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