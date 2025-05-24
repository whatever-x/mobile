package com.whatever.caramel.core.domain.entity

import kotlinx.datetime.LocalDate

data class Holiday(
    val id : Long,
    val date: LocalDate,
    val name: String,
    val isHoliday: Boolean
)