package com.whatever.caramel.core.domain_change.usecase.schedule

import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.repository.ScheduleRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class GetTodayScheduleUseCase(
    private val scheduleRepository: ScheduleRepository,
) {
    suspend operator fun invoke(): List<Todo> {
        val today =
            Clock.System
                .now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date
                .toString()

        return scheduleRepository.getTodos(
            startDate = today,
            endDate = today,
            userTimezone = null,
        )
    }
}
