package com.whatever.caramel.core.domain.vo.calendar

import com.whatever.caramel.core.domain.entity.Schedule

data class CalendarYear(
    val scheduleList: List<Schedule> = emptyList(),
    val anniversaryList: List<Anniversary> = emptyList(),
    val holidayList: List<Holiday> = emptyList(),
)
