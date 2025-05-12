package com.whatever.caramel.feature.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.feature.calendar.mvi.Schedule

@Composable
fun CalendarScheduleList(
    modifier: Modifier = Modifier,
    schedules: List<Schedule>,
    onClickTodo: (Long) -> Unit
) {
    val density = LocalDensity.current
    val spacingBetweenItems = density.run {
        CaramelTheme.spacing.xxs.roundToPx()
    }

    SubcomposeLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = CaramelTheme.spacing.xxs)
            .background(color = CaramelTheme.color.background.primary)
    ) { constraints ->
        val parentHeight = constraints.maxHeight
        var totalHeight = 0
        val itemsToPlace = mutableListOf<Pair<Placeable, Int>>()
        var visibleItemCount = 0
        var totalListSize = 0
        schedules.forEach { schedule ->
            totalListSize += when (schedule) {
                is Schedule.Holidays -> schedule.holidays.size
                is Schedule.Todos -> schedule.todos.size
            }
        }

        schedules.forEach { schedule ->
            when (schedule) {
                is Schedule.Holidays -> {
                    schedule.holidays.forEachIndexed { index, holiday ->
                        val placeable = subcompose("holiday_$index") {
                            CalendarHolidayItem(holiday = holiday)
                        }.first().measure(constraints)

                        val newHeight = totalHeight + placeable.height + spacingBetweenItems
                        if (newHeight + placeable.height <= parentHeight) {
                            visibleItemCount++
                            itemsToPlace.add(placeable to totalHeight)
                            totalHeight = newHeight
                        } else {
                            return@forEachIndexed
                        }
                    }
                }

                is Schedule.Todos -> {
                    schedule.todos.forEachIndexed { index, todo ->
                        val placeable = subcompose("todo_$index") {
                            CalendarTodoItem(todo = todo, onClickTodo = onClickTodo)
                        }.first().measure(constraints)

                        val newHeight = totalHeight + placeable.height + spacingBetweenItems
                        if (newHeight + placeable.height <= parentHeight) {
                            itemsToPlace.add(placeable to totalHeight)
                            totalHeight = newHeight
                            visibleItemCount++
                        } else {
                            return@forEachIndexed
                        }
                    }
                }
            }
        }

        val hasMoreItems = totalListSize > visibleItemCount
        if (hasMoreItems) {
            val morePlaceable = subcompose("more") {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = CaramelTheme.spacing.xs),
                    textAlign = TextAlign.Center,
                    style = CaramelTheme.typography.label3.regular,
                    color = CaramelTheme.color.text.secondary,
                    text = "+${totalListSize - visibleItemCount}개"
                )
            }.first().measure(constraints)
            itemsToPlace.add(morePlaceable to totalHeight + spacingBetweenItems)
        }

        layout(constraints.maxWidth, totalHeight) {
            itemsToPlace.forEach { (placeable, y) ->
                placeable.place(x = 0, y = y)
            }
        }
    }
}

@Composable
private fun CalendarHolidayItem(
    modifier: Modifier = Modifier,
    holiday: Holiday
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = CaramelTheme.color.fill.labelAccent1,
                shape = CaramelTheme.shape.xxs
            )
            .padding(horizontal = CaramelTheme.spacing.xxs),
        maxLines = 1,
        overflow = TextOverflow.Clip,
        text = holiday.name,
        style = CaramelTheme.typography.label3.bold,
        color = CaramelTheme.color.text.inverse
    )
}

@Composable
private fun CalendarTodoItem(
    modifier: Modifier = Modifier,
    todo: Todo,
    onClickTodo: (Long) -> Unit
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(
                // FIXME : 디자인 토큰 적용 필요
                color = Color(0xFFFFE6C3),
                shape = CaramelTheme.shape.xxs
            )
            .padding(horizontal = CaramelTheme.spacing.xxs)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = { onClickTodo(todo.id) }
            ),
        maxLines = 1,
        overflow = TextOverflow.Clip,
        text = todo.title,
        style = CaramelTheme.typography.label3.bold,
        color = CaramelTheme.color.text.labelBrand
    )
}