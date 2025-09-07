package com.whatever.caramel.feature.calendar.model

import com.whatever.caramel.core.domain.policy.ContentPolicy
import com.whatever.caramel.core.domain.vo.content.ContentAssignee

data class CalendarUiModel(
    val id: Long = NONE,
    val mainText: String = "",
    val type: ScheduleType = ScheduleType.SINGLE_SCHEDULE,
    val description: String = "",
    val originalScheduleSize: Long = 1L,
    val contentAssignee: ContentAssignee = ContentAssignee.US,
) {
    val url: String?
        get() {
            val allContent = "$mainText $description"
            return ContentPolicy.URL_PATTERN
                .findAll(allContent)
                .map { it.value }
                .firstOrNull()
        }

    enum class ScheduleType(
        val priority: Int,
    ) {
        MULTI_SCHEDULE(1),
        HOLIDAY(2),
        ANNIVERSARY(3),
        SINGLE_SCHEDULE(4),
    }

    companion object {
        const val NONE = -1L
    }
}
