package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.TodoList
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class GetTodosGroupByStartDateUseCase(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        userTimezone: String? = null
    ): List<TodoList> {
        return calendarRepository.getSchedules(
            startDate = startDate,
            endDate = endDate,
            userTimezone = userTimezone
        ).groupBy { it.startDate }
            .map { (date, todos) -> TodoList(date.date, todos) }
    }
}