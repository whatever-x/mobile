package com.whatever.caramel.feature.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.model.calendar.CalendarTodoModel
import com.whatever.caramel.core.domain.model.calendar.CalendarTodoType
import com.whatever.caramel.feature.calendar.mvi.CalendarDayState
import com.whatever.caramel.core.designsystem.util.clickableWithoutRipple

@Composable
fun CalendarDay(
    modifier: Modifier = Modifier,
    dayState: CalendarDayState,
    onClickDay: (Int) -> Unit,
) {
    Box(
        modifier = modifier
            .padding(
                horizontal = CaramelTheme.spacing.xxs,
                vertical = CaramelTheme.spacing.xs
            )
            .clickableWithoutRipple {
                onClickDay(dayState.day!!)
            }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            CalendarDayText(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                state = dayState
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clipToBounds()
            ) {
                if (dayState.todos.isEmpty()) return
                dayState.todos.forEach { todo ->
                    CalendarTodo(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        todo = todo
                    )
                }
            }
        }
    }

}

@Composable
fun CalendarDayText(
    modifier: Modifier = Modifier,
    state: CalendarDayState,
) {
    Box(
        modifier = modifier
            .background(
                color = if (state.isFocused) {
                    CaramelTheme.color.fill.primary
                } else {
                    Color.Unspecified
                },
                shape = CaramelTheme.shape.s
            )
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 3.dp),
            text = state.day.toString(),
            style = CaramelTheme.typography.body4.regular,
            color = when {
                state.isFocused -> CaramelTheme.color.text.inverse
                state.isHoliday -> CaramelTheme.color.text.labelAccent1
                state.isSaturday -> CaramelTheme.color.text.labelAccent2
                else -> CaramelTheme.color.text.primary
            }
        )
    }
}

@Composable
fun CalendarTodo(
    modifier: Modifier,
    todo: CalendarTodoModel,
) {
    Box(
        modifier = modifier
            .background(
                color = when (todo.type) {
                    CalendarTodoType.NATIONAL_HOLIDAY -> CaramelTheme.color.fill.labelAccent1
                    CalendarTodoType.ANNIVERSARY -> CaramelTheme.color.fill.labelAccent2
                    CalendarTodoType.TODO -> CaramelTheme.color.fill.labelBrand
                    CalendarTodoType.OVER -> CaramelTheme.color.background.primary
                },
                shape = CaramelTheme.shape.xxs
            )
            .padding(
                horizontal = CaramelTheme.spacing.xxs,
                vertical = CaramelTheme.spacing.xxs
            )
    ) {
        Text(
            modifier = Modifier
                .align(
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
            }
        )
    }
}
