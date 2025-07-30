package com.whatever.caramel.core.domain.vo.schedule

import kotlinx.datetime.LocalDate

data class Holiday(
    val date: LocalDate,
    val name: String,
    val isHoliday: Boolean,
)
