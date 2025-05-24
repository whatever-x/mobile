package com.whatever.caramel.core.domain.vo.calendar

data object Calendar {
    private const val MIN_YEAR = 1900
    private const val MAX_YEAR = 2100
    val yearSize = MAX_YEAR - MIN_YEAR + 1

    val YEAR_RANGE = MIN_YEAR..MAX_YEAR
}
