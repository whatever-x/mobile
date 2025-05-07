package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.TodoList
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toLocalDateTime

class GetSchedulesUseCaseGroupByStartDate(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        userTimezone: String? = null
    ): List<TodoList> {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            .date
            .atTime(hour = 0, minute = 0)
        val schedules = calendarRepository.getSchedules(
            startDate = startDate,
            endDate = endDate,
            userTimezone = userTimezone
        ).groupBy { it.startDate }
            .map { (date, todos) -> TodoList(date, todos) }
            .sortedBy { it.dateTime }
        if (schedules.find { it.dateTime.date == today.date } == null) {
            schedules.toMutableList().add(TodoList(dateTime = today, todos = emptyList()))
        }
        return schedules
    }
}