package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.HolidayList

class GetHolidaysUseCase(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(year: Int, monthNumber: Int): List<HolidayList> {
        val monthString = monthNumber.toString().padStart(2, '0')
        return calendarRepository.getHolidays(
            year = year,
            monthString = monthString
        ).groupBy { it.date }
            .map { (date, holidayList) -> HolidayList(date, holidayList) }
    }
}