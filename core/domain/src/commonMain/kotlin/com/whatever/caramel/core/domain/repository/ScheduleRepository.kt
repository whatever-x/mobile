package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.params.schedule.ScheduleEditParameter
import com.whatever.caramel.core.domain.params.schedule.ScheduleParameter
import com.whatever.caramel.core.domain.vo.schedule.Holiday

interface ScheduleRepository {
    suspend fun createSchedule(parameter: ScheduleParameter): ScheduleMetadata

    suspend fun updateSchedule(
        scheduleId: Long,
        parameter: ScheduleEditParameter,
    )

    suspend fun getSchedules(
        startDate: String,
        endDate: String,
        userTimezone: String?,
    ): List<Schedule>

    suspend fun deleteSchedule(scheduleId: Long)

    suspend fun getHolidays(year: Int): List<Holiday>

    suspend fun getSchedule(scheduleId: Long): ScheduleDetail
}
