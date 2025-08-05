package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.calendar.HolidayDetailListResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleAnniversaryResponse

interface RemoteCalendarDataSource {

    suspend fun fetchHolidayListByYear(year: String): HolidayDetailListResponse

    // @ham2174 TODO : GET /v1/calendar 캘린더 조회 API 연결
    // @ham2174 TODO : GET /v1/calendar/holidays 휴일 조회 API 연결

}
