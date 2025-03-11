package com.whatever.caramel.feature.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.model.calendar.CalendarDayModel
import com.whatever.caramel.core.domain.model.calendar.CalendarModel
import com.whatever.caramel.core.domain.model.calendar.CalendarTodoType
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun CaramelCalendar(
    calendarModel: CalendarModel,
    modifier: Modifier = Modifier,
    onDateClick: (CalendarDayModel) -> Unit = {}
) {
    val current = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val currentMonth = current.date.month.ordinal + 1
    val currentYear = current.year

    var selectedDay by remember(calendarModel) {
        mutableStateOf(
            if (calendarModel.month == currentMonth && calendarModel.year == currentYear) {
                calendarModel.day[current.dayOfMonth - 1].day
            } else {
                1
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        DayOfWeekHeader()
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            CalendarCell(
                calendarModel = calendarModel,
                onDateClick = { dayModel ->
                    selectedDay = if (selectedDay == dayModel.day) selectedDay else dayModel.day
                    onDateClick(dayModel)
                },
                selectedDay = selectedDay
            )
        }
    }
}

@Composable
fun CalendarCell(
    calendarModel: CalendarModel,
    selectedDay: Int,
    onDateClick: (CalendarDayModel) -> Unit = {}
) {
    val firstDayOfWeek = calendarModel.day.firstOrNull()?.weekDay?.ordinal ?: 0
    val totalDays = calendarModel.day.size
    val totalRows = (totalDays + firstDayOfWeek + 6) / 7

    Column {
        for (row in 0 until totalRows) {
            val isStartSunday = (row * 7 - firstDayOfWeek - 1)
            if (isStartSunday == -7) {
                continue
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (col in 0..6) {
                    val index = (row * 7 + col - firstDayOfWeek) - 1

                    if (index in 0 until totalDays) {
                        val dayModel = calendarModel.day[index]
                        val isFocused = selectedDay == dayModel.day
                        CalendarDayItem(
                            dayModel = dayModel,
                            isFocused = isFocused,
                            onClick = {
                                onDateClick(dayModel)
                            },
                            modifier = Modifier.weight(1f),
                        )
                    } else {
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarDayItem(
    dayModel: CalendarDayModel,
    isFocused: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = CaramelTheme.spacing.xxs, vertical = CaramelTheme.spacing.xs)
            .clickable(onClick = {
                onClick()
            }),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = if (isFocused) {
                            CaramelTheme.color.fill.primary
                        } else {
                            Color.Unspecified
                        }, shape = CaramelTheme.shape.s
                    )
            ) {
                // 날짜 숫자
                Text(
                    modifier = Modifier
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    text = dayModel.day.toString(),
                    style = CaramelTheme.typography.body4.regular,
                    color = if (isFocused) {
                        CaramelTheme.color.text.inverse
                    } else {
                        when {
                            dayModel.isHoliday() -> CaramelTheme.color.text.labelAccent1  // 휴일(일요일 또는 공휴일)
                            dayModel.weekDay == DayOfWeek.SATURDAY -> CaramelTheme.color.text.labelAccent2  // 토요일
                            else -> CaramelTheme.color.text.primary  // 평일
                        }
                    },
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clipToBounds()
            ) {
                if (dayModel.todos.isNotEmpty()) {
                    val sortedTodos = dayModel.todos.sortedBy { it.type.priority }
                    if (sortedTodos.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        for (todo in sortedTodos) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 2.dp)
                                    .background(
                                        color = when (todo.type) {
                                            CalendarTodoType.NATIONAL_HOLIDAY -> CaramelTheme.color.fill.labelAccent1
                                            CalendarTodoType.ANNIVERSARY -> CaramelTheme.color.fill.labelAccent2
                                            CalendarTodoType.TODO -> CaramelTheme.color.fill.labelBrand
                                            CalendarTodoType.OVER -> CaramelTheme.color.background.primary
                                        },
                                        shape = CaramelTheme.shape.xxs
                                    )
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    modifier = Modifier.align(
                                        if (todo.type == CalendarTodoType.OVER) {
                                            Alignment.Center
                                        } else {
                                            Alignment.CenterStart
                                        }
                                    ),
                                    text = todo.description,
                                    style = CaramelTheme.typography.label3.bold,
                                    overflow = TextOverflow.Visible,
                                    maxLines = 1,
                                    color = when (todo.type) {
                                        CalendarTodoType.NATIONAL_HOLIDAY -> CaramelTheme.color.text.inverse
                                        CalendarTodoType.ANNIVERSARY -> CaramelTheme.color.text.inverse
                                        CalendarTodoType.TODO -> CaramelTheme.color.text.labelBrand
                                        CalendarTodoType.OVER -> CaramelTheme.color.text.secondary
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}