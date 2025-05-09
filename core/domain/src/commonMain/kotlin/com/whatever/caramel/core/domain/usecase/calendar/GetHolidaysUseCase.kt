package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import kotlinx.datetime.LocalDate

class GetHolidaysUseCase(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(date: LocalDate): List<Holiday> {
        return calendarRepository.getHolidays(
            year = date.year,
            month = date.monthNumber
        )
    }
}