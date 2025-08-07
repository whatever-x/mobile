package com.whatever.caramel.core.domain.usecase.schedule

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.repository.ScheduleRepository

class GetScheduleUseCase(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(scheduleId: Long): Schedule =
        scheduleRepository.getSchedule(scheduleId)
}