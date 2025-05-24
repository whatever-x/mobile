package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toHoliday
import com.whatever.caramel.core.data.mapper.toTodo
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.entity.Holiday
import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.remote.datasource.RemoteCalendarDataSource

class CalendarRepositoryImpl(
    private val remoteCalendarDataSource: RemoteCalendarDataSource
) : CalendarRepository {
    override suspend fun getTodos(
        startDate: String,
        endDate: String,
        userTimezone: String?
    ): List<Todo> {
        return safeCall {
            remoteCalendarDataSource.getSchedules(startDate, endDate, userTimezone).toTodo()
        }
    }

    override suspend fun getHolidays(year: Int): List<Holiday> {
        return safeCall {
            val yearString = year.toString()
            remoteCalendarDataSource.getHolidays(year = yearString).toHoliday()
        }
    }
}