package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toHoliday
import com.whatever.caramel.core.data.mapper.toSchedules
import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.core.remote.datasource.RemoteCalendarDataSource

class CalendarRepositoryImpl(
    private val remoteCalendarDataSource: RemoteCalendarDataSource
) : CalendarRepository {
    override suspend fun getSchedules(
        startDate: String,
        endDate: String,
        userTimezone: String?
    ): List<Todo> {
        return remoteCalendarDataSource.getSchedules(startDate, endDate, userTimezone).toSchedules()
    }

    override suspend fun getHolidays(year: Int, month: Int): List<Holiday> {
        val yearMonth = "$year-$month"
        return remoteCalendarDataSource.getHolidays(yearMonth).toHoliday()
    }
}