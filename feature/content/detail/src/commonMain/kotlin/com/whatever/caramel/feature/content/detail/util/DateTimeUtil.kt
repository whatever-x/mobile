package com.whatever.caramel.feature.content.detail.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

private fun LocalDateTime.toDateText() = "${year}년 ${month.number}월 ${day}일"

private fun LocalDateTime.toTimeText(): String {
    val hourText = hour.toString().padStart(2, '0')
    val minuteText = minute.toString().padStart(2, '0')
    return "$hourText:$minuteText"
}

fun LocalDateTime.toDisplayText(isAllDay: Boolean): String = if (isAllDay) toDateText() else "${toDateText()}\n${toTimeText()}"
