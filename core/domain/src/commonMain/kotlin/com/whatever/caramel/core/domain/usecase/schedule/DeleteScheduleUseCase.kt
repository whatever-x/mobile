package com.whatever.caramel.core.domain.usecase.schedule

import com.whatever.caramel.core.domain.repository.ScheduleRepository

class DeleteScheduleUseCase(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(scheduleId: Long) {
        scheduleRepository.deleteSchedule(scheduleId)
    }
}