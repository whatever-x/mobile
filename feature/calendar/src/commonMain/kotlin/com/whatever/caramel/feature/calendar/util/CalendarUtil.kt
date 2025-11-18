package com.whatever.caramel.feature.calendar.util

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.number

internal val DayOfWeek.appOrdianl: Int
    get() = (this.ordinal + 1) % 7

internal fun getYearAndMonthFromPageIndex(index: Int): Pair<Int, Month> {
    val year = (index / 12) + 1900
    val month = Month.entries[index % 12]
    return year to month
}

internal fun LocalDate.weekOfMonth(firstDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY): Int {
    val firstDayOfMonth = LocalDate(this.year, month.number, 1)
    val dayOfWeekIndex =
        (firstDayOfMonth.dayOfWeek.isoDayNumber - firstDayOfWeek.isoDayNumber + 7) % 7
    val dayIndex = day + dayOfWeekIndex - 1

    return (dayIndex / 7)
}

internal fun LocalDateTime.weekOfMonth(firstDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY) = this.date.weekOfMonth(firstDayOfWeek)
