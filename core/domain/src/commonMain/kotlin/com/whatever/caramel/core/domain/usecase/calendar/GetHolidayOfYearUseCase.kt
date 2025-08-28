package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.Holiday

class GetHolidayOfYearUseCase(
    private val calendarRepository: CalendarRepository,
) {
    suspend operator fun invoke(year: Int): List<Holiday> =
        calendarRepository
            .getHolidayList(
                year = year,
            )
}
