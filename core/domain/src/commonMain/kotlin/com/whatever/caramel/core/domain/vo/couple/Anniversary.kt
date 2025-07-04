package com.whatever.caramel.core.domain.vo.couple

import kotlinx.datetime.LocalDate

data class Anniversary(
    val type: AnniversaryType,
    val date: LocalDate,
    val label: String,
    val isAdjustedForNonLeapYear: Boolean,
)
