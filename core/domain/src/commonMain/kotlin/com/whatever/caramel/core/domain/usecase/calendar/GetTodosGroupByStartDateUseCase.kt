package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.TodoOnDate

class GetTodosGroupByStartDateUseCase(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        userTimezone: String? = null
    ): List<TodoOnDate> {
        return calendarRepository.getSchedules(
            startDate = startDate,
            endDate = endDate,
            userTimezone = userTimezone
        ).groupBy { it.startDate }
            .map { (date, todos) -> TodoOnDate(date.date, todos) }
    }
}