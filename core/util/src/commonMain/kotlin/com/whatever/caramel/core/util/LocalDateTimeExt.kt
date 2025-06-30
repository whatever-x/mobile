package com.whatever.caramel.core.util

import kotlinx.datetime.LocalDateTime

fun LocalDateTime.copy(
    year: Int = this.year,
    monthNumber: Int = this.monthNumber,
    dayOfMonth: Int = this.dayOfMonth,
    hour: Int = this.hour,
    minute: Int = this.minute,
    second: Int = this.second,
    nanosecond: Int = this.nanosecond,
): LocalDateTime =
    LocalDateTime(
        year = year,
        monthNumber = monthNumber,
        dayOfMonth = dayOfMonth,
        hour = hour,
        minute = minute,
        second = second,
        nanosecond = nanosecond,
    )
