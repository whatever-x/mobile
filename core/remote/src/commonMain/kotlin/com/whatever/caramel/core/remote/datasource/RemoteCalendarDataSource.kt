package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.calendar.CalendarDetailResponse
import com.whatever.caramel.core.remote.dto.calendar.HolidayDetailListResponse
import com.whatever.caramel.core.remote.dto.calendar.request.CreateScheduleRequest
import com.whatever.caramel.core.remote.dto.calendar.response.CreateScheduleResponse
import com.whatever.caramel.core.remote.dto.calendar.request.UpdateScheduleRequest

interface RemoteCalendarDataSource {
    suspend fun createSchedule(request: CreateScheduleRequest): CreateScheduleResponse

    suspend fun getSchedules(
        startDate: String,
        endDate: String,
        userTimeZone: String?
    ): CalendarDetailResponse

    suspend fun getHolidaysByYear(
        year: String
    ): HolidayDetailListResponse

    suspend fun updateSchedule(
        scheduleId: Long,
        updateScheduleRequest: UpdateScheduleRequest
    )

    suspend fun deleteSchedule(scheduleId: Long)
}