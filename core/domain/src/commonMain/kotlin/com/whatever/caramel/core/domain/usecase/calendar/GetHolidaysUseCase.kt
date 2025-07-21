package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.HolidaysOnDate

class GetHolidaysUseCase(
    private val calendarRepository: CalendarRepository,
) {
    suspend operator fun invoke(year: Int): List<HolidaysOnDate> =
        calendarRepository
            .getHolidays(
                year = year,
            ).groupBy { it.date }
            .map { (date, holidayList) -> HolidaysOnDate(date, holidayList) }
}
