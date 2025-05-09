package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.TodoList
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toLocalDateTime

class GetSchedulesGroupByStartDateUseCase(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        userTimezone: String? = null
    ): List<TodoList> {
        val result = mutableListOf<TodoList>()
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        val schedules = calendarRepository.getSchedules(
            startDate = startDate,
            endDate = endDate,
            userTimezone = userTimezone
        ).groupBy { it.startDate }
            .map { (date, todos) -> TodoList(date.date, todos) }

        result.addAll(schedules)
        if (schedules.find { it.date == today } == null) {
            result.add(TodoList(date = today, todos = emptyList()))
        }
        result.sortBy { it.date }
        return result
    }
}