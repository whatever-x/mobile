package com.whatever.caramel.feature.content.util

import kotlinx.datetime.LocalDateTime

private fun LocalDateTime.toDateText() = "${year}년 ${monthNumber}월 ${dayOfMonth}일"

private fun LocalDateTime.toTimeText() = "${hour}:${minute}"

fun isAllDay(startDateTime : LocalDateTime, endDateTime : LocalDateTime) : Boolean {
    return startDateTime.hour == 0 && startDateTime.minute == 0 && endDateTime.hour == 23 && endDateTime.minute == 59
}

fun LocalDateTime.toDisplayText(isAllDay: Boolean): String =
    if (isAllDay) toDateText() else "${toDateText()}\n${toTimeText()}"