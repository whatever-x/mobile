package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Holiday
import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.vo.calendar.ScheduleMetadata
import com.whatever.caramel.core.domain.vo.calendar.ScheduleParameter

interface CalendarRepository {
    suspend fun createSchedule(parameter: ScheduleParameter): ScheduleMetadata

    suspend fun getTodos(
        startDate: String,
        endDate: String,
        userTimezone: String?
    ): List<Todo>

    suspend fun getHolidays(
        year: Int
    ): List<Holiday>
}