package com.whatever.caramel.core.domain.vo.calendar

import kotlinx.datetime.LocalDate

data class AnniversaryOnDate(
    val date: LocalDate,
    val anniversaryList: List<Anniversary>,
)
