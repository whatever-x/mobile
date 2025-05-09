package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.vo.calendar.Holiday

interface CalendarRepository {
    suspend fun getSchedules(
        startDate : String,
        endDate : String,
        userTimezone : String?
    ) : List<Todo>

    suspend fun getHolidays(
        year : Int,
        monthString : String
    ) : List<Holiday>
}