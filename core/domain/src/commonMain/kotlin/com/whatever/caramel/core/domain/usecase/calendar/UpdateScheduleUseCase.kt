package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.ScheduleEditParameter

class UpdateScheduleUseCase(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(scheduleId: Long, parameter: ScheduleEditParameter) {
        calendarRepository.updateSchedule(scheduleId, parameter)
    }
} 