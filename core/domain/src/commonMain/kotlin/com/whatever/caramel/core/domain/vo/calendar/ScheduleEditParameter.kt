package com.whatever.caramel.core.domain.vo.calendar

import com.whatever.caramel.core.domain.vo.common.DateTimeInfo

data class ScheduleEditParameter(
    val selectedDate: String,
    val title: String?,
    val description: String?,
    val isCompleted: Boolean,
    val dateTimeInfo: DateTimeInfo?,
    val tagIds: List<Long>,
)
