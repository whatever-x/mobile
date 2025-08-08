package com.whatever.caramel.core.domain.vo.calendar

import kotlinx.datetime.LocalDate

data class Holiday(
    val date: LocalDate,
    val name: String,
    val isHoliday: Boolean,
)
