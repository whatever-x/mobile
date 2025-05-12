package com.whatever.caramel.feature.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.calendar.mvi.Schedule
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

@Composable
internal fun CalendarDayOfMonthCell(
    modifier: Modifier = Modifier,
    schedules: List<Schedule>,
    date: LocalDate,
    isFocus: Boolean,
    onClickCell: (LocalDate) -> Unit = {},
    onClickTodo: (Long) -> Unit = {}
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = { onClickCell(date) }
                ),
            verticalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.xxs)
        ) {
            CalendarDayOfMonthText(
                modifier = Modifier.fillMaxWidth(),
                dayOfWeek = date.dayOfWeek,
                dayOfMonth = date.dayOfMonth,
                isFocus = isFocus,
                isHoliday = schedules.find { it is Schedule.Holidays } != null
            )
            CalendarScheduleList(
                schedules = schedules,
                onClickTodo = { onClickTodo(it) }
            )
        }
    }
}

@Composable
private fun CalendarDayOfMonthText(
    modifier: Modifier = Modifier,
    dayOfWeek: DayOfWeek,
    dayOfMonth: Int,
    isFocus: Boolean,
    isHoliday: Boolean
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .background(
                color = if (isFocus) {
                    CaramelTheme.color.fill.primary
                } else {
                    Color.Unspecified
                },
                shape = CaramelTheme.shape.s
            )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = dayOfMonth.toString(),
            style = CaramelTheme.typography.body4.regular,
            color = when {
                isFocus -> CaramelTheme.color.text.inverse
                dayOfWeek == DayOfWeek.SATURDAY -> CaramelTheme.color.text.labelAccent2
                isHoliday || dayOfWeek == DayOfWeek.SUNDAY -> CaramelTheme.color.text.labelAccent1
                else -> CaramelTheme.color.text.primary
            }
        )
    }
}