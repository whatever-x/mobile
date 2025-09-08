package com.whatever.caramel.core.domain.policy

data object CalendarPolicy {
    const val MIN_YEAR = 1900
    const val MAX_YEAR = 2100

    const val YEAR_SIZE = MAX_YEAR - MIN_YEAR + 1

    const val DAY_OF_WEEK = 7
}
