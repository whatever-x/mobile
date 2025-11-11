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
import caramel.core.designsystem.generated.resources.Res
import caramel.core.designsystem.generated.resources.day_of_week
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.policy.CalendarPolicy
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.feature.calendar.model.CalendarCell
import com.whatever.caramel.feature.calendar.model.CalendarCellUiModel
import com.whatever.caramel.feature.calendar.util.appOrdianl
import com.whatever.caramel.feature.calendar.util.getYearAndMonthFromPageIndex
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import org.jetbrains.compose.resources.stringArrayResource
import kotlin.math.ceil

@Composable
internal fun CaramelCalendar(
    modifier: Modifier = Modifier,
    pageIndex: Int,
    selectedDate: LocalDate,
    monthCellMap: ImmutableMap<CalendarCell, List<CalendarCellUiModel>>,
    yearHolidaySet: ImmutableSet<LocalDate>,
    onClickSchedule: (Long) -> Unit,
    onClickCell: (LocalDate) -> Unit,
) {
    val (year, month) = getYearAndMonthFromPageIndex(index = pageIndex)
    val firstDay = LocalDate(year = year, month = month, day = 1)
    val firstDayOfWeek = firstDay.dayOfWeek.appOrdianl
    val lastDay = DateUtil.getLastDayOfMonth(year, month.number)
    val totalCells = firstDayOfWeek + lastDay
    val weekendCount = ceil(totalCells.toFloat() / CalendarPolicy.DAY_OF_WEEK).toInt()

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val cellHeight = maxHeight / weekendCount
        Column(modifier = Modifier.fillMaxSize()) {
            repeat(weekendCount) { weekendIndex ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(CalendarPolicy.DAY_OF_WEEK) { colIndex ->
                        val index = weekendIndex * CalendarPolicy.DAY_OF_WEEK + colIndex
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
                                dayOfMonth = date.day,
                                dayOfWeek = date.dayOfWeek,
                                isFocus = selectedDate == date,
                                isHoliday = date in yearHolidaySet,
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
                        monthCellMap.get(
                            key =
                                CalendarCell(
                                    pageIndex = pageIndex,
                                    weekendIndex = weekendIndex,
                                ),
                        ) ?: emptyList(),
                    onClickCell = onClickSchedule,
                )
            }
        }
    }
}

@Composable
internal fun CalendarDayOfWeek(modifier: Modifier = Modifier) {
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
