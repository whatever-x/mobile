package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.calendar.HolidayDetailListResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Named

internal class RemoteCalendarDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient,
) : RemoteCalendarDataSource {
    override suspend fun fetchHolidayListByYear(year: String): HolidayDetailListResponse =
        authClient
            .get("$CALENDAR_BASE_URL/holidays/year") {
                parameter("year", year)
            }.getBody()

    companion object {
        private const val CALENDAR_BASE_URL = "/v1/calendar"
    }
}
