package com.whatever.caramel.core.domain.usecase.schedule

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.repository.ScheduleRepository

class GetScheduleInPeriodUseCase(
    private val scheduleRepository: ScheduleRepository,
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
    ): List<Schedule> =
        scheduleRepository
            .getScheduleList(
                startDate = startDate,
                endDate = endDate,
            )
}
