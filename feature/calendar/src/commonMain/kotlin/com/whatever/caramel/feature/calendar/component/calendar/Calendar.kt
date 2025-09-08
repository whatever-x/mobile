package com.whatever.caramel.feature.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import caramel.feature.calendar.generated.resources.Res
import caramel.feature.calendar.generated.resources.day_of_week
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.feature.calendar.model.CalendarCell
import com.whatever.caramel.feature.calendar.model.CalendarUiModel
import com.whatever.caramel.feature.calendar.util.appOrdianl
import com.whatever.caramel.feature.calendar.util.getFirstDayOffset
import com.whatever.caramel.feature.calendar.util.getYearAndMonthFromPageIndex
import com.whatever.caramel.feature.calendar.util.weekOfMonth
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import org.jetbrains.compose.resources.stringArrayResource
import kotlin.math.ceil

@Composable
fun CaramelCalendar(
    modifier: Modifier = Modifier,
    pageIndex: Int,
    selectedDate: LocalDate,
    monthCellInfoList: ImmutableList<CalendarCell>,
    onClickSchedule: (Long) -> Unit,
    onClickCell: (LocalDate) -> Unit,
) {
    val (year, month) = getYearAndMonthFromPageIndex(index = pageIndex)
    val firstDay = LocalDate(year = year, month = month, dayOfMonth = 1)
    val firstDayOfWeek = firstDay.dayOfWeek.appOrdianl
    val lastDay = DateUtil.getLastDayOfMonth(year, month.number)
    val totalCells = firstDayOfWeek + lastDay
    val weekendCount = ceil(totalCells.toFloat() / 7).toInt()

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val cellHeight = maxHeight / weekendCount
        Column(modifier = Modifier.fillMaxSize()) {
            repeat(weekendCount) { weekendIndex ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(7) { colIndex ->
                        val index = weekendIndex * 7 + colIndex
                        val dayOfMonth = index - firstDayOfWeek + 1
                        val boxModifier =
                            Modifier
                                .weight(1f)
                                .height(24.dp)

                        if (dayOfMonth in 1..lastDay) {
                            val date = LocalDate(year, month, dayOfMonth)
                            CalendarDayOfMonthText(
                                modifier =
                                    boxModifier.clickable(
                                        indication = null,
                                        interactionSource = null,
                                        onClick = { onClickCell(date) },
                                    ),
                                dayOfMonth = date.dayOfMonth,
                                dayOfWeek = date.dayOfWeek,
                                isFocus = selectedDate == date,
                                isHoliday =
                                    monthCellInfoList.any { cell ->
                                        cell.weekendIndex == date.weekOfMonth() &&
                                            cell.scheduleList.any {
                                                it.base.type == CalendarUiModel.ScheduleType.HOLIDAY
                                            }
                                    },
                            )
                        } else {
                            Spacer(modifier = boxModifier)
                        }
                    }
                }
                CalendarScheduleList(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(height = cellHeight - 24.dp)
                            .background(color = CaramelTheme.color.background.primary),
                    cellUiList =
                        monthCellInfoList.firstOrNull { it.weekendIndex == weekendIndex }?.scheduleList
                            ?: emptyList(),
                    onClickCell = onClickSchedule,
                )
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
