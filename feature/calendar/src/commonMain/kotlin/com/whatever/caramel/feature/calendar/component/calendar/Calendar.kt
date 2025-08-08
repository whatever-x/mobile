package com.whatever.caramel.feature.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import caramel.feature.calendar.generated.resources.Res
import caramel.feature.calendar.generated.resources.day_of_week
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.feature.calendar.mvi.DaySchedule
import com.whatever.caramel.feature.calendar.util.getFirstDayOffset
import com.whatever.caramel.feature.calendar.util.getYearAndMonthFromPageIndex
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import org.jetbrains.compose.resources.stringArrayResource

@Composable
fun CaramelCalendar(
    modifier: Modifier = Modifier,
    pageIndex: Int,
    selectedDate: LocalDate,
    schedules: ImmutableList<DaySchedule>,
    onClickSchedule: (Long) -> Unit,
    onClickCell: (LocalDate) -> Unit,
) {
    val scheduleMap =
        remember(schedules) {
            schedules.associateBy { it.date }
        }
    val (year, month) = getYearAndMonthFromPageIndex(index = pageIndex)
    val firstDay = LocalDate(year = year, month = month, dayOfMonth = 1)
    val firstDayOfWeek = getFirstDayOffset(firstDay)
    val lastDay = DateUtil.getLastDayOfMonth(year, month.number)
    val totalCells = firstDayOfWeek + lastDay
    val column = (totalCells + 6) / 7

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(7),
            userScrollEnabled = false,
        ) {
            val cellHeight = maxHeight / column
            items(totalCells) { index ->
                val dayOfMonth = index - firstDayOfWeek + 1
                val boxModifier =
                    Modifier
                        .height(cellHeight)
                        .fillMaxWidth()
                Box(modifier = boxModifier) {
                    if (dayOfMonth in 1..lastDay) {
                        val date = LocalDate(year, month, dayOfMonth)
                        CalendarDayOfMonthCell(
                            modifier = Modifier.fillMaxSize(),
                            schedule = scheduleMap[date],
                            date = date,
                            isFocus = selectedDate == date,
                            onClickCell = { onClickCell(it) },
                            onClickSchedule = { onClickSchedule(it) },
                        )
                    } else {
                        Spacer(modifier = boxModifier)
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarDayOfWeek(modifier: Modifier = Modifier) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(CaramelTheme.color.background.primary)
                .padding(top = CaramelTheme.spacing.s, bottom = CaramelTheme.spacing.m),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        stringArrayResource(Res.array.day_of_week).forEach { dayOfWeek ->
            Text(
                text = dayOfWeek,
                style = CaramelTheme.typography.label2.bold,
                color = CaramelTheme.color.text.primary,
                textAlign = TextAlign.Center,
            )
        }
    }
}
