package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toSchedules
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
        return remoteCalendarDataSource.getSchedules(startDate, endDate, userTimezone).toSchedules()
    }
}