package com.whatever.caramel.core.domain_change.usecase.schedule

import com.whatever.caramel.core.domain.repository.ScheduleRepository

class GetScheduleUseCase(
    private val scheduleRepository: ScheduleRepository,
) {
    suspend operator fun invoke(scheduleId: Long): ScheduleDetail = scheduleRepository.getSchedule(scheduleId)
}
