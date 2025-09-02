package com.whatever.caramel.feature.calendar.util

import androidx.compose.runtime.Composable
import caramel.feature.calendar.generated.resources.Res
import caramel.feature.calendar.generated.resources.friday
import caramel.feature.calendar.generated.resources.monday
import caramel.feature.calendar.generated.resources.saturday
import caramel.feature.calendar.generated.resources.sunday
import caramel.feature.calendar.generated.resources.thursday
import caramel.feature.calendar.generated.resources.tuesday
import caramel.feature.calendar.generated.resources.wednesday
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.isoDayNumber
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DayOfWeek.toUiText(): String =
    stringResource(
        when (this) {
            DayOfWeek.MONDAY -> Res.string.monday
            DayOfWeek.TUESDAY -> Res.string.tuesday
            DayOfWeek.WEDNESDAY -> Res.string.wednesday
            DayOfWeek.THURSDAY -> Res.string.thursday
            DayOfWeek.FRIDAY -> Res.string.friday
            DayOfWeek.SATURDAY -> Res.string.saturday
            DayOfWeek.SUNDAY -> Res.string.sunday
        },
    )


fun LocalDate.weekOfMonth(firstDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY): Int {
    val firstDayOfMonth = LocalDate(this.year, this.monthNumber, 1)
    // 이번 달 1일의 요일
    val dayOfWeekIndex = (firstDayOfMonth.dayOfWeek.isoDayNumber - firstDayOfWeek.isoDayNumber + 7) % 7
    // 며칠째인지 (0-based)
    val dayIndex = this.dayOfMonth + dayOfWeekIndex - 1

    return (dayIndex / 7)
}

fun LocalDateTime.weekOfMonth(firstDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY): Int {
    val firstDayOfMonth = LocalDate(this.year, this.monthNumber, 1)
    // 이번 달 1일의 요일
    val dayOfWeekIndex = (firstDayOfMonth.dayOfWeek.isoDayNumber - firstDayOfWeek.isoDayNumber + 7) % 7
    // 며칠째인지 (0-based)
    val dayIndex = this.dayOfMonth + dayOfWeekIndex - 1

    return (dayIndex / 7)
}