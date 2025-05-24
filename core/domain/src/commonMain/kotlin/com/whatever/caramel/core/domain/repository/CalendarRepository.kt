package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Holiday
import com.whatever.caramel.core.domain.entity.Todo

interface CalendarRepository {
    suspend fun getSchedules(
        startDate : String,
        endDate : String,
        userTimezone : String?
    ) : List<Todo>

    suspend fun getHolidays(
        year : Int
    ) : List<Holiday>
}