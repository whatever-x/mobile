package com.whatever.caramel.core.domain.usecase.schedule

import com.whatever.caramel.core.domain.repository.ScheduleRepository
import com.whatever.caramel.core.domain.vo.schedule.ScheduleOnDate

class GetScheduleInPeriodUseCase(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
    ): List<ScheduleOnDate> =
        scheduleRepository
            .getScheduleList(
                startDate = startDate,
                endDate = endDate,
            ).groupBy { it.dateTimeInfo.startDateTime }
            .map { (date, todos) -> ScheduleOnDate(date.date, todos) }
}
