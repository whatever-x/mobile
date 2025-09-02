package com.whatever.caramel.feature.calendar.util

import com.whatever.caramel.feature.calendar.mvi.CalendarSchedule
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month

internal fun getYearAndMonthFromPageIndex(index: Int): Pair<Int, Month> {
    val year = (index / 12) + 1900
    val month = Month.entries[index % 12]
    return year to month
}

internal fun getFirstDayOffset(firstDay: LocalDate): Int = if (firstDay.dayOfWeek == DayOfWeek.SUNDAY) 0 else firstDay.dayOfWeek.ordinal + 1
