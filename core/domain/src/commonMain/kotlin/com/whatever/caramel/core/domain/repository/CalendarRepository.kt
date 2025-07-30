package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Holiday
import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.content.schedule.EditScheduleParameter
import com.whatever.caramel.core.domain.vo.content.schedule.CreateScheduleParameter

interface CalendarRepository {
    suspend fun createSchedule(parameter: CreateScheduleParameter)

    suspend fun updateSchedule(
        scheduleId: Long,
        parameter: EditScheduleParameter,
    )

    suspend fun getTodos(
        startDate: String,
        endDate: String,
        userTimezone: String?,
    ): List<Schedule>

    suspend fun deleteSchedule(scheduleId: Long)

    suspend fun getHolidays(year: Int): List<Holiday>

    suspend fun getSchedule(scheduleId: Long): Schedule
}
