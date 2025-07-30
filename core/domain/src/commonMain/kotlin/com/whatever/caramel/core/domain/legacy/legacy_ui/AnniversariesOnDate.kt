package com.whatever.caramel.core.domain.legacy.legacy_ui

import com.whatever.caramel.core.domain.vo.couple.Anniversary
import kotlinx.datetime.LocalDate

// UI 모델
data class AnniversariesOnDate(
    val date: LocalDate,
    val anniversaries: List<Anniversary>,
)