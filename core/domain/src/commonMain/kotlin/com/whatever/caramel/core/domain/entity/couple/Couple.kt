package com.whatever.caramel.core.domain.entity.couple

import kotlinx.datetime.LocalDate

data class Couple(
    val id: Long,
    val startDate: LocalDate,
    val sharedMessage: String
)
