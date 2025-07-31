package com.whatever.caramel.core.domain_change.usecase.schedule

import com.whatever.caramel.core.domain.repository.ScheduleRepository
import com.whatever.caramel.core.domain.params.schedule.ScheduleEditParameter

class UpdateScheduleUseCase(
    private val scheduleRepository: ScheduleRepository,
) {
    suspend operator fun invoke(
        scheduleId: Long,
        parameter: ScheduleEditParameter,
    ) {
        scheduleRepository.updateSchedule(scheduleId, parameter)
    }
}
