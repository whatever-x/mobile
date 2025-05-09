package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.calendar.CalendarDetailResponse
import com.whatever.caramel.core.remote.dto.calendar.HolidayDetailListResponse

interface RemoteCalendarDataSource {
    suspend fun getSchedules(
        startDate: String,
        endDate: String,
        userTimeZone: String?
    ): CalendarDetailResponse

    suspend fun getHolidays(
        yearMonth: String
    ): HolidayDetailListResponse
}