package com.whatever.caramel.core.datasource.remote

import com.whatever.caramel.core.datasource.remote.dto.calendar.HolidayDetailListResponse

interface RemoteCalendarDataSource {
    suspend fun fetchHolidayListByYear(year: String): HolidayDetailListResponse

    // @ham2174 TODO : GET /v1/calendar 캘린더 조회 API 연결
    // @ham2174 TODO : GET /v1/calendar/holidays 휴일 조회 API 연결
}
