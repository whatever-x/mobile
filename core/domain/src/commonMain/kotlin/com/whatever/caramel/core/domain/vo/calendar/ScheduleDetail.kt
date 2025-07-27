package com.whatever.caramel.core.domain.vo.calendar

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.vo.content.ContentAssignee

data class ScheduleDetail(
    val scheduleId: Long,
    val startDateTime: String,
    val endDateTime: String,
    val startDateTimezone: String,
    val endDateTimezone: String,
    val isCompleted: Boolean,
    val parentScheduleId: Long?,
    val title: String,
    val description: String,
    val tags: List<Tag>,
    val role : ContentAssignee,
)
