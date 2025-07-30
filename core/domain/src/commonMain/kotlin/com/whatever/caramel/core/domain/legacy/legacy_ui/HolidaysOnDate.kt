package com.whatever.caramel.core.domain.legacy.legacy_ui

import com.whatever.caramel.core.domain.vo.schedule.Holiday
import kotlinx.datetime.LocalDate

data class HolidaysOnDate(
    val date: LocalDate,
    val holidays: List<Holiday>,
)