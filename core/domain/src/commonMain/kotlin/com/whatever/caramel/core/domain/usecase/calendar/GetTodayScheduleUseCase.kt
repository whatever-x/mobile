package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.repository.CalendarRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class GetTodayScheduleUseCase(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(): List<Todo> {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()

        return calendarRepository.getTodos(
            startDate = today,
            endDate = today,
            userTimezone = null
        )
    }
}