package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.calendar.CalendarDetailResponse
import com.whatever.caramel.core.remote.dto.calendar.request.CreateScheduleRequest
import com.whatever.caramel.core.remote.dto.calendar.request.UpdateScheduleRequest
import com.whatever.caramel.core.remote.dto.calendar.response.CreateScheduleResponse
import com.whatever.caramel.core.remote.dto.calendar.response.GetScheduleResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

class RemoteScheduleDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient,
) : RemoteScheduleDataSource {
    override suspend fun createSchedule(request: CreateScheduleRequest): CreateScheduleResponse =
        authClient
            .post(SCHEDULE_BASE_URL) {
                setBody(body = request)
            }.getBody()

    override suspend fun fetchScheduleList(
        startDate: String,
        endDate: String,
    ): CalendarDetailResponse =
        authClient
            .get(SCHEDULE_BASE_URL) {
                parameter("startDate", startDate)
                parameter("endDate", endDate)
            }.getBody()

    override suspend fun updateSchedule(
        scheduleId: Long,
        updateScheduleRequest: UpdateScheduleRequest,
    ) {
        authClient.put("$SCHEDULE_BASE_URL/$scheduleId") {
            setBody(updateScheduleRequest)
        }
    }

    override suspend fun deleteSchedule(scheduleId: Long) {
        authClient.delete("$SCHEDULE_BASE_URL/$scheduleId")
    }

    override suspend fun fetchSchedule(scheduleId: Long): GetScheduleResponse = authClient.get("$SCHEDULE_BASE_URL/$scheduleId").getBody()

    companion object {
        private const val SCHEDULE_BASE_URL = "/v1/calendar/schedules"
    }
}
