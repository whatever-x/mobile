package com.whatever.caramel.core.domain.vo.calendar

import com.whatever.caramel.core.domain.vo.couple.Anniversary
import kotlinx.datetime.LocalDate

data class AnniversariesOnDate(
    val date: LocalDate,
    val anniversaries: List<Anniversary>,
)
