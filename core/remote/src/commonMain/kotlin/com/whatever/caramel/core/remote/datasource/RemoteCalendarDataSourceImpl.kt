package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.calendar.CalendarDetailResponse
import com.whatever.caramel.core.remote.dto.calendar.HolidayDetailListResponse
import com.whatever.caramel.core.remote.dto.calendar.request.CreateScheduleRequest
import com.whatever.caramel.core.remote.dto.calendar.response.CreateScheduleResponse
import com.whatever.caramel.core.remote.dto.calendar.request.UpdateScheduleRequest
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

internal class RemoteCalendarDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient
) : RemoteCalendarDataSource {
    override suspend fun createSchedule(request: CreateScheduleRequest): CreateScheduleResponse =
        authClient.post("$CALENDAR_BASE_URL/schedules") {
            setBody(body = request)
        }.getBody()

    override suspend fun getSchedules(
        startDate: String,
        endDate: String,
        userTimeZone: String?
    ): CalendarDetailResponse {
        return authClient.get(CALENDAR_BASE_URL) {
            parameter("startDate", startDate)
            parameter("endDate", endDate)
            parameter("userTimeZone", userTimeZone)
        }.getBody()
    }

    override suspend fun getHolidaysByYear(year: String): HolidayDetailListResponse {
        return authClient.get("$CALENDAR_BASE_URL/holidays/year") {
            parameter("year", year)
        }.getBody()
    }

    override suspend fun updateSchedule(scheduleId: Long, updateScheduleRequest: UpdateScheduleRequest) {
        authClient.put("$CALENDAR_BASE_URL/schedules/$scheduleId") {
            setBody(updateScheduleRequest)
        }
    }

    override suspend fun deleteSchedule(scheduleId: Long) {
        authClient.delete("$CALENDAR_BASE_URL/schedules/$scheduleId")
    }

    companion object {
        private const val CALENDAR_BASE_URL = "v1/calendar"
    }
} 