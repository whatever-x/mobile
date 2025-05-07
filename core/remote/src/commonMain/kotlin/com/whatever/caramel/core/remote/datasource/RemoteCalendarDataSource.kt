package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.calendar.CalendarDetailResponse

interface RemoteCalendarDataSource {
    suspend fun getSchedules(
        startDate : String,
        endDate : String,
        userTimeZone : String?
    ) : CalendarDetailResponse
}