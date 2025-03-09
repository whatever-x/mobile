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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.model.calendar.CalendarDayModel
import com.whatever.caramel.core.domain.model.calendar.CalendarModel
import com.whatever.caramel.core.domain.model.calendar.CalendarTodoType
import kotlinx.datetime.DayOfWeek

@Composable
fun CaramelCalendar(
    calendarModel: CalendarModel,
    modifier: Modifier = Modifier,
    onDateClick: (CalendarDayModel) -> Unit = {}
) {
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
                onDateClick = onDateClick
            )
        }
    }
}

@Composable
fun CalendarCell(
    calendarModel: CalendarModel,
    onDateClick: (CalendarDayModel) -> Unit = {}
) {
    val firstDayOfWeek = calendarModel.day.firstOrNull()?.weekDay?.ordinal ?: 0
    val totalDays = calendarModel.day.size
    val totalRows = (totalDays + firstDayOfWeek + 6) / 7
    Column {
        for (row in 0 until totalRows) {
            val isStartSunday = (row * 7 - firstDayOfWeek - 1)
            if(isStartSunday == -7){
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
                        CalendarDayItem(
                            dayModel = dayModel,
                            onClick = { onDateClick(dayModel) },
                            modifier = Modifier.weight(1f)
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
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(CaramelTheme.spacing.xxs)
            .clickable(onClick = onClick),
    ) {
        Column {
            // 날짜 숫자
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally),
                    text = dayModel.day.toString(),
                    style = CaramelTheme.typography.body4.regular,
                    color = when {
                        dayModel.isHoliday() -> CaramelTheme.color.text.labelAccent1  // 휴일(일요일 또는 공휴일)
                        dayModel.weekDay == DayOfWeek.SATURDAY -> CaramelTheme.color.text.labelAccent2  // 토요일
                        else -> CaramelTheme.color.text.primary  // 평일
                    },
                )
            }

            if (dayModel.todos.isNotEmpty()) {
                val sortedTodos = dayModel.todos.sortedBy { it.type.priority }
                if (sortedTodos.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Box(
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .background(
                                color = when (sortedTodos.first().type) {
                                    CalendarTodoType.NATIONAL_HOLIDAY -> Color.Red
                                    CalendarTodoType.ANNIVERSARY -> Color.Green
                                    CalendarTodoType.TODO -> Color.Blue
                                    CalendarTodoType.OVER -> Color.Gray
                                },
                                shape = CaramelTheme.shape.xxs
                            )
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = sortedTodos.first().description,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // 추가 항목이 있음을 표시
                    if (sortedTodos.size > 1) {
                        Text(
                            text = "+${sortedTodos.size - 1}",
                            style = CaramelTheme.typography.label3.regular,
                            color = CaramelTheme.color.text.secondary
                        )
                    }
                }
            }
        }
    }
}