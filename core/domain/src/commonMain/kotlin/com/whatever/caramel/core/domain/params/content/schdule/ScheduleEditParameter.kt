package com.whatever.caramel.core.domain.params.content.schdule

import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.schedule.DateTimeInfo

data class ScheduleEditParameter(
    val selectedDate: String,
    val title: String?,
    val description: String?,
    val isCompleted: Boolean,
    val dateTimeInfo: DateTimeInfo?,
    val tagIds: List<Long>,
    val contentAssignee: ContentAssignee,
)
