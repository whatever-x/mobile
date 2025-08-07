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
import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.feature.calendar.mvi.DaySchedule

@Composable
fun CalendarScheduleList(
    modifier: Modifier = Modifier,
    schedule: DaySchedule,
    onClickTodo: (Long) -> Unit,
) {
    val density = LocalDensity.current
    val spacingBetweenItems =
        density.run {
            CaramelTheme.spacing.xxs.roundToPx()
        }

    SubcomposeLayout(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = CaramelTheme.spacing.xxs)
                .background(color = CaramelTheme.color.background.primary),
    ) { constraints ->
        val parentHeight = constraints.maxHeight
        var totalHeight = 0
        val itemsToPlace = mutableListOf<Pair<Placeable, Int>>()
        var visibleItemCount = 0
        val totalListSize = schedule.totalScheduleCount

        schedule.holidayList.forEachIndexed { index, holiday ->
            val placeable =
                subcompose("holiday_$index") {
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

        schedule.anniversaryList.forEachIndexed { index, anniversary ->
            val placeable =
                subcompose("anniversary_$index") {
                    CalendarAnniversaryItem(anniversary = anniversary)
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

        schedule.scheduleList.forEachIndexed { index, todo ->
            val placeable =
                subcompose("todo_$index") {
                    CalendarScheduleItem(schedule = todo, onClickTodo = onClickTodo)
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

        val hasMoreItems = totalListSize > visibleItemCount
        if (hasMoreItems) {
            val morePlaceable =
                subcompose("more") {
                    Text(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = CaramelTheme.spacing.xs),
                        textAlign = TextAlign.Center,
                        style = CaramelTheme.typography.label3.regular,
                        color = CaramelTheme.color.text.secondary,
                        text = "+${totalListSize - visibleItemCount}개",
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
private fun ScheduleItem(
    modifier: Modifier = Modifier,
    content: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit = {},
) {
    Text(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    color = backgroundColor,
                    shape = CaramelTheme.shape.xxs,
                ).padding(horizontal = CaramelTheme.spacing.xxs)
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = onClick,
                ),
        maxLines = 1,
        overflow = TextOverflow.Clip,
        text = content,
        style = CaramelTheme.typography.label3.bold,
        color = textColor,
    )
}

@Composable
private fun CalendarAnniversaryItem(
    modifier: Modifier = Modifier,
    anniversary: Anniversary,
) {
    ScheduleItem(
        modifier = modifier,
        content = anniversary.label,
        backgroundColor = CaramelTheme.color.fill.brand,
        textColor = CaramelTheme.color.text.inverse,
    )
}

@Composable
private fun CalendarHolidayItem(
    modifier: Modifier = Modifier,
    holiday: Holiday,
) {
    ScheduleItem(
        modifier = modifier,
        content = holiday.name,
        backgroundColor = CaramelTheme.color.fill.labelAccent1,
        textColor = CaramelTheme.color.text.inverse,
    )
}

@Composable
private fun CalendarScheduleItem(
    modifier: Modifier = Modifier,
    schedule: Schedule,
    onClickTodo: (Long) -> Unit,
) {
    val (textColor, backgroundColor) =
        when (schedule.contentData.contentAssignee) {
            ContentAssignee.ME -> CaramelTheme.color.text.labelAccent4 to CaramelTheme.color.fill.labelAccent3
            ContentAssignee.PARTNER -> CaramelTheme.color.text.labelAccent3 to CaramelTheme.color.fill.labelAccent4
            ContentAssignee.US -> CaramelTheme.color.text.labelBrand to CaramelTheme.color.fill.labelBrand
        }

    ScheduleItem(
        modifier = modifier,
        content = schedule.contentData.title.ifEmpty { schedule.contentData.description },
        backgroundColor = backgroundColor,
        textColor = textColor,
        onClick = { onClickTodo(schedule.id) },
    )
}
