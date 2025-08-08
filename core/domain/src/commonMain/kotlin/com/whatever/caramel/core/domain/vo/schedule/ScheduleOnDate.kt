package com.whatever.caramel.core.domain.vo.schedule

import com.whatever.caramel.core.domain.entity.Schedule
import kotlinx.datetime.LocalDate

data class ScheduleOnDate(
    val date: LocalDate,
    val scheduleList: List<Schedule>,
)
