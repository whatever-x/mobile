package com.whatever.caramel.core.domain.vo.calendar

import com.whatever.caramel.core.domain.vo.common.DateTimeInfo
import com.whatever.caramel.core.domain.vo.content.ContentAssignee

data class ScheduleEditParameter(
    val selectedDate: String,
    val title: String?,
    val description: String?,
    val isCompleted: Boolean,
    val dateTimeInfo: DateTimeInfo?,
    val tagIds: List<Long>,
    val contentAssignee: ContentAssignee,
)
