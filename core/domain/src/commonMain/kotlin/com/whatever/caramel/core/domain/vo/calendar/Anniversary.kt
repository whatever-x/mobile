package com.whatever.caramel.core.domain.vo.calendar

import com.whatever.caramel.core.domain.vo.calendar.AnniversaryType
import kotlinx.datetime.LocalDate

data class Anniversary(
    val type: AnniversaryType,
    val date: LocalDate,
    val label: String,
)