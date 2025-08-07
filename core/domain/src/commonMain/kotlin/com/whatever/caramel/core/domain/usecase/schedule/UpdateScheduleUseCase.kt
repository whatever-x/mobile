package com.whatever.caramel.core.domain.usecase.schedule

import com.whatever.caramel.core.domain.params.content.schdule.ScheduleEditParameter
import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.repository.ScheduleRepository

class UpdateScheduleUseCase(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(
        scheduleId: Long,
        parameter: ScheduleEditParameter,
    ) {
        scheduleRepository.updateSchedule(scheduleId, parameter)
    }
}