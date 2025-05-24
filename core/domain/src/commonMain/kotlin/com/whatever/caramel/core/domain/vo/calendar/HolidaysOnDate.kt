package com.whatever.caramel.core.domain.vo.calendar

import com.whatever.caramel.core.domain.entity.Holiday
import kotlinx.datetime.LocalDate

data class HolidaysOnDate (
    val date: LocalDate,
    val holidays: List<Holiday>
)