package com.whatever.caramel.feature.calendar.model

import com.whatever.caramel.core.domain.vo.content.ContentAssignee

data class CalendarUiModel(
    val id: Long? = null,
    val mainText: String = "",
    val type: ScheduleType = ScheduleType.SINGLE_SCHEDULE,
    val description: String? = null,
    val contentAssignee: ContentAssignee? = null,
) {
    enum class ScheduleType(val priority: Int) {
        MULTI_SCHEDULE(1),
        HOLIDAY(2),
        ANNIVERSARY(3),
        SINGLE_SCHEDULE(4),
        ;
    }
}