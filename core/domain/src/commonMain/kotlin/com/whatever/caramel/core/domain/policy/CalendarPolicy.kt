package com.whatever.caramel.core.domain.policy

// yearSize와 YEAR_RANGE는 UI 모델임
data object CalendarPolicy {
    const val MIN_YEAR = 1900
    const val MAX_YEAR = 2100
}