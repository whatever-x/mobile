package com.whatever.caramel.feature.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import caramel.feature.calendar.generated.resources.Res
import caramel.feature.calendar.generated.resources.day_of_week
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.feature.calendar.mvi.DaySchedule
import com.whatever.caramel.feature.calendar.util.getFirstDayOffset
import com.whatever.caramel.feature.calendar.util.getYearAndMonthFromPageIndex
import io.github.aakira.napier.Napier
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
    val scheduleMap = remember(schedules) {
        schedules.associateBy { it.date }
    }

    val (year, month) = getYearAndMonthFromPageIndex(index = pageIndex)
    val firstDay = LocalDate(year = year, month = month, dayOfMonth = 1)
    val firstDayOfWeek = getFirstDayOffset(firstDay)
    val lastDay = DateUtil.getLastDayOfMonth(year, month.number)
    val totalCells = firstDayOfWeek + lastDay
    val rowCount = (totalCells + 6) / 7 // 주 단위 row 개수

    Napier.d { "rowCount : $rowCount" }
    Napier.d { "totalCells : $totalCells" }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val cellHeight = maxHeight / rowCount
        // 주차를 그린다
        Column(modifier = Modifier.fillMaxSize()) {
            // 주차별 repeat
            repeat(rowCount) { rowIndex ->
                // 해당주차의 요일을 그린다
                Row(modifier = Modifier.fillMaxWidth()) {
                    // 요일 반복
                    repeat(7) { colIndex ->
                        val index = rowIndex * 7 + colIndex
                        val dayOfMonth = index - firstDayOfWeek + 1
                        val boxModifier =
                            Modifier
                                .weight(1f)
                                .height(24.dp)

                        if (dayOfMonth in 1..lastDay) {
                            val date = LocalDate(year, month, dayOfMonth)
                            CalendarDayOfMonthCell(
                                modifier = boxModifier,
                                schedule = scheduleMap[date],
                                date = date,
                                isFocus = selectedDate == date,
                                onClickCell = { onClickCell(it) },
                                onClickSchedule = { onClickSchedule(it) },
                            )
                        } else {
                            Spacer(modifier = boxModifier) // 빈 칸
                        }
                    }
                }
                Text(modifier = Modifier.fillMaxWidth()
                    .height(height = cellHeight - 24.dp), text = "123123123123123123123123123123123123123123")
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
