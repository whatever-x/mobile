package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.core.remote.dto.calendar.CalendarDetailResponse
import com.whatever.caramel.core.remote.dto.calendar.HolidayDetailListResponse
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDate

fun CalendarDetailResponse.toSchedules(): List<Todo> {
    return this.calendarResult.scheduleList.map {
        Todo(
            id = it.scheduleId,
            startDate = LocalDateTime.parse(it.startDateTime),
            endDate = LocalDateTime.parse(it.endDateTime),
            title = it.title ?: "",
            description = it.description ?: ""
        )
    }
}

fun HolidayDetailListResponse.toHoliday(): List<Holiday> {
    return this.holidayList.map {
        Holiday(
            date = LocalDate.parse(it.date),
            name = it.name
        )
    }
}