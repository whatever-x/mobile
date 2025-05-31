package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.ScheduleDetail

class GetScheduleDetailUseCase(
    private val calendarRepository: CalendarRepository,
) {
    suspend operator fun invoke(scheduleId: Long): ScheduleDetail {
        return calendarRepository.getScheduleDetail(scheduleId)
    }
} 