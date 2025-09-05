package com.whatever.caramel.feature.calendar.model

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday

data class CalendarYearCache(
    val year: Int,
    val totalList: List<CacheModel> = emptyList(),
) {
    data class CacheModel(
        val scheduleList: List<Schedule> = emptyList(),
        val anniversary: List<Anniversary> = emptyList(),
        val holiday: List<Holiday> = emptyList(),
    )
}
