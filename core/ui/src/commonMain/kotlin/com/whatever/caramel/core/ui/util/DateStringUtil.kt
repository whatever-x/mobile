package com.whatever.caramel.core.ui.util

import androidx.compose.runtime.Composable
import caramel.core.designsystem.generated.resources.Res
import caramel.core.designsystem.generated.resources.day_of_week
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.stringArrayResource

@Composable
fun DayOfWeek.toUiText(): String {
    val dayOfWeek = stringArrayResource(Res.array.day_of_week)
    val index = if (this == DayOfWeek.SUNDAY) 0 else (this.ordinal + 1)
    return dayOfWeek[index]
}

private fun LocalDateTime.toDateText() = "${year}년 ${monthNumber}월 ${dayOfMonth}일"

private fun LocalDateTime.toTimeText(): String {
    val hourText = hour.toString().padStart(2, '0')
    val minuteText = minute.toString().padStart(2, '0')
    return "$hourText:$minuteText"
}

fun LocalDateTime.toDisplayText(isAllDay: Boolean): String = if (isAllDay) toDateText() else "${toDateText()}\n${toTimeText()}"
