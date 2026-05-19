package com.whatever.caramel.core.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

fun LocalDateTime.copy(
    year: Int = this.year,
    monthNumber: Int = month.number,
    dayOfMonth: Int = day,
    hour: Int = this.hour,
    minute: Int = this.minute,
    second: Int = this.second,
    nanosecond: Int = this.nanosecond,
): LocalDateTime =
    LocalDateTime(
        year = year,
        month = monthNumber,
        day = dayOfMonth,
        hour = hour,
        minute = minute,
        second = second,
        nanosecond = nanosecond,
    )
