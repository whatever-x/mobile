package com.whatever.caramel.feature.calendar.model

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday

data class CalendarCacheModel(
    val scheduleList: List<Schedule> = emptyList(),
    val anniversaryList: List<Anniversary> = emptyList(),
    val holidayList: List<Holiday> = emptyList(),
)
