package com.whatever.caramel.core.domain.policy

data object CalendarPolicy {
    const val MIN_YEAR = 1900
    const val MAX_YEAR = 2100

    val YEAR_SIZE
        get() = MAX_YEAR - MIN_YEAR + 1
}