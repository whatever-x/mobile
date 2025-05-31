package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Holiday
import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.vo.calendar.ScheduleDetail
import com.whatever.caramel.core.domain.vo.calendar.ScheduleMetadata
import com.whatever.caramel.core.domain.vo.calendar.ScheduleParameter
import com.whatever.caramel.core.domain.vo.calendar.ScheduleEditParameter

interface CalendarRepository {
    suspend fun createSchedule(parameter: ScheduleParameter): ScheduleMetadata

    suspend fun updateSchedule(scheduleId: Long, parameter: ScheduleEditParameter)

    suspend fun getTodos(
        startDate: String,
        endDate: String,
        userTimezone: String?
    ): List<Todo>

    suspend fun deleteSchedule(scheduleId: Long)

    suspend fun getHolidays(
        year: Int
    ): List<Holiday>

    suspend fun getScheduleDetail(scheduleId: Long): ScheduleDetail
}