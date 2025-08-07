package com.whatever.caramel.core.domain.usecase.schedule

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.repository.ScheduleRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class GetTodayScheduleUseCase(
    private val scheduleRepository: ScheduleRepository,
) {
    suspend operator fun invoke(): List<Schedule> {
        val today =
            Clock.System
                .now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date
                .toString()

        return scheduleRepository.getScheduleList(
            startDate = today,
            endDate = today
        )
    }
}
