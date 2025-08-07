package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.HolidayOnDate

class GetHolidayOfYearUseCase(
    private val calendarRepository: CalendarRepository,
) {
    suspend operator fun invoke(year: Int): List<HolidayOnDate> =
        calendarRepository.getHolidayList(
            year = year,
        ).groupBy { it.date }
            .map { (date, holidayList) -> HolidayOnDate(date, holidayList) }
}
