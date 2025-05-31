package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository

class DeleteScheduleUseCase(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(scheduleId: Long) {
        calendarRepository.deleteSchedule(scheduleId)
    }
} 