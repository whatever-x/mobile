package com.whatever.caramel.core.datasource.remote

import com.whatever.caramel.core.datasource.remote.dto.calendar.CalendarDetailResponse
import com.whatever.caramel.core.datasource.remote.dto.calendar.request.CreateScheduleRequest
import com.whatever.caramel.core.datasource.remote.dto.calendar.request.UpdateScheduleRequest
import com.whatever.caramel.core.datasource.remote.dto.calendar.response.CreateScheduleResponse
import com.whatever.caramel.core.datasource.remote.dto.calendar.response.GetScheduleResponse

interface RemoteScheduleDataSource {
    suspend fun createSchedule(request: CreateScheduleRequest): CreateScheduleResponse

    suspend fun fetchScheduleList(
        startDate: String,
        endDate: String,
    ): CalendarDetailResponse

    suspend fun fetchSchedule(scheduleId: Long): GetScheduleResponse

    suspend fun updateSchedule(
        scheduleId: Long,
        updateScheduleRequest: UpdateScheduleRequest,
    )

    suspend fun deleteSchedule(scheduleId: Long)
}
