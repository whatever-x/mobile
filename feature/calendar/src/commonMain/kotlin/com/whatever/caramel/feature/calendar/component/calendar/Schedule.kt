package com.whatever.caramel.feature.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.feature.calendar.mvi.DaySchedule
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

@Composable
fun CalendarScheduleList(
    modifier: Modifier = Modifier,
    daySchedule: DaySchedule,
    onClickSchedule: (Long) -> Unit,
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
                .background(color = CaramelTheme.color.background.primary),
    ) { constraints ->
        val parentHeight = constraints.maxHeight
        var totalHeight = 0
        val itemsToPlace = mutableListOf<Pair<Placeable, Int>>()
        var visibleItemCount = 0
        val totalListSize = daySchedule.totalScheduleCount

        daySchedule.holidayList.forEachIndexed { index, holiday ->
            val placeable =
                subcompose("holiday_$index") {
                    CalendarHolidayItem(holiday = holiday, currentDate = daySchedule.date)
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

        daySchedule.anniversaryList.forEachIndexed { index, anniversary ->
            val placeable =
                subcompose("anniversary_$index") {
                    CalendarAnniversaryItem(
                        anniversary = anniversary,
                        currentDate = daySchedule.date,
                    )
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

        daySchedule.scheduleList.forEachIndexed { index, schedule ->
            val placeable =
                subcompose("schedule_$index") {
                    CalendarScheduleItem(
                        schedule = schedule,
                        onClickSchedule = onClickSchedule,
                        currentDate = daySchedule.date,
                    )
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
    isContentVisible: Boolean,
    isStartSchedule: Boolean,
    isEndSchedule: Boolean,
    onClick: () -> Unit = {},
) {
    val (paddingModifier, shape) =
        when {
            isStartSchedule && isEndSchedule ->
                modifier.padding(horizontal = CaramelTheme.spacing.xxs) to
                    RoundedCornerShape(
                        size = 2.dp,
                    )

            isStartSchedule ->
                modifier.padding(start = CaramelTheme.spacing.xxs) to
                    RoundedCornerShape(
                        topStart = 2.dp,
                        bottomStart = 2.dp,
                    )

            isEndSchedule ->
                modifier.padding(end = CaramelTheme.spacing.xxs) to
                    RoundedCornerShape(
                        topEnd = 2.dp,
                        bottomEnd = 2.dp,
                    )

            else -> modifier to RectangleShape
        }

    Text(
        modifier =
            modifier
                .then(
                    paddingModifier,
                ).background(
                    color = backgroundColor,
                    shape = shape,
                ).then(paddingModifier)
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = onClick,
                ),
        maxLines = 1,
        overflow = TextOverflow.Clip,
        text = if (isContentVisible) content else "",
        style = CaramelTheme.typography.label3.bold,
        color = textColor,
    )
}

@Composable
private fun CalendarAnniversaryItem(
    modifier: Modifier = Modifier,
    anniversary: Anniversary,
    currentDate: LocalDate,
) {
    ScheduleItem(
        modifier = modifier,
        content = anniversary.label,
        backgroundColor = CaramelTheme.color.fill.brand,
        textColor = CaramelTheme.color.text.inverse,
        isStartSchedule = currentDate == anniversary.date,
        isEndSchedule = currentDate == anniversary.date,
        isContentVisible = currentDate == anniversary.date,
    )
}

@Composable
private fun CalendarHolidayItem(
    modifier: Modifier = Modifier,
    holiday: Holiday,
    currentDate: LocalDate,
) {
    ScheduleItem(
        modifier = modifier,
        content = holiday.name,
        backgroundColor = CaramelTheme.color.fill.labelAccent1,
        textColor = CaramelTheme.color.text.inverse,
        isStartSchedule = currentDate == holiday.date,
        isEndSchedule = currentDate == holiday.date,
        isContentVisible = currentDate == holiday.date,
    )
}

@Composable
private fun CalendarScheduleItem(
    modifier: Modifier = Modifier,
    schedule: Schedule,
    currentDate: LocalDate,
    onClickSchedule: (Long) -> Unit,
) {
    val (textColor, backgroundColor) =
        when (schedule.contentData.contentAssignee) {
            ContentAssignee.ME -> CaramelTheme.color.text.labelAccent4 to CaramelTheme.color.fill.labelAccent3
            ContentAssignee.PARTNER -> CaramelTheme.color.text.labelAccent3 to CaramelTheme.color.fill.labelAccent4
            ContentAssignee.US -> CaramelTheme.color.text.labelBrand to CaramelTheme.color.fill.labelBrand
        }

    val isStartSchedule =
        with(schedule.dateTimeInfo.startDateTime.date) {
            currentDate == this || currentDate.dayOfWeek == DayOfWeek.SUNDAY || currentDate.dayOfMonth == 1
        }
    val isEndSchedule =
        with(schedule.dateTimeInfo.endDateTime.date) {
            currentDate == this ||
                currentDate.dayOfWeek == DayOfWeek.SATURDAY ||
                currentDate.dayOfMonth ==
                DateUtil.getLastDayOfMonth(
                    this.year,
                    this.monthNumber,
                )
        }

    ScheduleItem(
        modifier = modifier,
        content = schedule.contentData.title.ifEmpty { schedule.contentData.description },
        backgroundColor = backgroundColor,
        textColor = textColor,
        onClick = { onClickSchedule(schedule.id) },
        isStartSchedule = isStartSchedule,
        isEndSchedule = isEndSchedule,
        isContentVisible = currentDate == schedule.dateTimeInfo.startDateTime.date,
    )
}
