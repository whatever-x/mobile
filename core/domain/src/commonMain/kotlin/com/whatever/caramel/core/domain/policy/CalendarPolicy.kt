package com.whatever.caramel.core.domain.policy

data object CalendarPolicy {
    const val MIN_YEAR = 1900
    const val MAX_YEAR = 2100

    val YEAR_RANGE = MIN_YEAR..MAX_YEAR

    const val TOTAL_MONTH_SIZE = 12

    const val TOTAL_YEAR_SIZE = MAX_YEAR - MIN_YEAR + 1
}
