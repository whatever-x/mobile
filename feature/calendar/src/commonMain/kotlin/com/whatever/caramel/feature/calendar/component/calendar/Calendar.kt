package com.whatever.caramel.feature.calendar.component.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import caramel.feature.calendar.generated.resources.Res
import caramel.feature.calendar.generated.resources.day_of_week
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.feature.calendar.mvi.Schedule
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.number
import org.jetbrains.compose.resources.stringArrayResource

@Composable
fun CaramelCalendar(
    modifier: Modifier = Modifier,
    year: Int,
    month: Month,
    schedules: List<Schedule>
) {
    val firstDay = LocalDate(year = year, month = month, dayOfMonth = 1)
    val firstDayOfWeek =
        if (firstDay.dayOfWeek == DayOfWeek.SUNDAY) 0 else firstDay.dayOfWeek.ordinal + 1
    val lastDay = DateUtil.getLastDayOfMonth(year, month.number)

    Column(modifier = modifier) {
        CalendarDayOfWeek()

        val totalCells = firstDayOfWeek + lastDay
        val totalRows = (totalCells + 6) / 7
        repeat(totalRows) { row ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(7) { column ->
                        val dayOfMonth = row * 7 + column - firstDayOfWeek + 1
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            if (dayOfMonth in 1..lastDay) {
                                val date = LocalDate(year, month, dayOfMonth)
                                CalendarDayOfMonthCell(
                                    schedules = schedules.filter { it.date == date },
                                    date = date,
                                    isFocus = false,
                                    onClickCell = {},
                                    onClickTodo = {}
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarDayOfWeek() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = CaramelTheme.spacing.s, bottom = CaramelTheme.spacing.m),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        stringArrayResource(Res.array.day_of_week).forEach { dayOfWeek ->
            Text(
                text = dayOfWeek,
                style = CaramelTheme.typography.label2.bold,
                color = CaramelTheme.color.text.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}