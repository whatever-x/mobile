package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toHoliday
import com.whatever.caramel.core.data.mapper.toSchedules
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.entity.Holiday
import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.remote.datasource.RemoteCalendarDataSource

class CalendarRepositoryImpl(
    private val remoteCalendarDataSource: RemoteCalendarDataSource
) : CalendarRepository {
    override suspend fun getSchedules(
        startDate: String,
        endDate: String,
        userTimezone: String?
    ): List<Todo> {
        return safeCall {
            remoteCalendarDataSource.getSchedules(startDate, endDate, userTimezone).toSchedules()
        }
    }

    override suspend fun getHolidays(year: Int, monthString: String): List<Holiday> {
        val yearMonth = "$year-$monthString"
        return safeCall {
            remoteCalendarDataSource.getHolidays(yearMonth).toHoliday()
        }
    }
}