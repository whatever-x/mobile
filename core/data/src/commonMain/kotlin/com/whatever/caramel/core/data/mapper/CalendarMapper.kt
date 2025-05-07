package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.remote.dto.calendar.CalendarDetailResponse
import kotlinx.datetime.LocalDateTime

fun CalendarDetailResponse.toSchedules() : List<Todo> {
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