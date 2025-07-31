package com.whatever.caramel.core.domain.usecase.schedule

import com.whatever.caramel.core.domain.repository.ScheduleRepository
import com.whatever.caramel.core.domain.model.schedule.TodosOnDate

/**
 *
 * */
class GetTodosGroupByStartDateUseCase(
    private val scheduleRepository: ScheduleRepository,
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        userTimezone: String? = null,
    ): List<TodosOnDate> =
        scheduleRepository
            .getTodos(
                startDate = startDate,
                endDate = endDate,
                userTimezone = userTimezone,
            ).groupBy { it.startDate }
            .map { (date, todos) -> TodosOnDate(date.date, todos) }
}
