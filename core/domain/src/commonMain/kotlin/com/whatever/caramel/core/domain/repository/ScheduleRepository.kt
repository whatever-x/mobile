package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleEditParameter
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleParameter

interface ScheduleRepository {

    suspend fun createSchedule(parameter: ScheduleParameter)

    suspend fun getSchedule(scheduleId: Long): Schedule

    suspend fun updateSchedule(
        scheduleId: Long,
        parameter: ScheduleEditParameter,
    )

    suspend fun getScheduleList(
        startDate: String,
        endDate: String,
        userTimezone: String?,
    ): List<Schedule>

    suspend fun deleteSchedule(scheduleId: Long)

}