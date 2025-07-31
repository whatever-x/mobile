package com.whatever.caramel.core.domain.usecase.schedule

import com.whatever.caramel.core.domain.repository.ScheduleRepository
import com.whatever.caramel.core.domain.model.schedule.HolidaysOnDate

class GetHolidaysUseCase(
    private val scheduleRepository: ScheduleRepository,
) {
    suspend operator fun invoke(year: Int): List<HolidaysOnDate> =
        scheduleRepository
            .getHolidays(
                year = year,
            ).groupBy { it.date }
            .map { (date, holidayList) -> HolidaysOnDate(date, holidayList) }
}
