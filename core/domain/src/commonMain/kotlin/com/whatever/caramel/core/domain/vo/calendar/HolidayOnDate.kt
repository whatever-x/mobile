package com.whatever.caramel.core.domain.vo.calendar

import kotlinx.datetime.LocalDate

data class HolidayOnDate(
    val date: LocalDate,
    val holidayList: List<Holiday>
)