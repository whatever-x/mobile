package com.whatever.caramel.feature.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.policy.CalendarPolicy
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.feature.calendar.dimension.CalendarDimension
import com.whatever.caramel.feature.calendar.model.CalendarCellUiModel
import com.whatever.caramel.feature.calendar.model.CalendarUiModel

@Composable
internal fun CalendarScheduleList(
    modifier: Modifier = Modifier,
    cellUiList: List<CalendarCellUiModel>,
    onClickCell: (Long) -> Unit,
) {
    val density = LocalDensity.current
    val spacingBetweenItemsPx = with(density) { CaramelTheme.spacing.xxs.roundToPx() }
    val scheduleCellHeightPx = with(density) { CalendarDimension.scheduleCellHeight.roundToPx() }
    val totalCellHeight = scheduleCellHeightPx + spacingBetweenItemsPx
    SubcomposeLayout(modifier = modifier) { constraints ->
        val parentHeight = constraints.maxHeight
        val schedulePerWidth = (constraints.maxWidth / CalendarPolicy.DAY_OF_WEEK)
        val itemsToPlace = mutableListOf<Triple<Placeable, Int, Int>>()
        val outRangeArray = IntArray(CalendarPolicy.DAY_OF_WEEK)
        val maxVisibleItemCount = (parentHeight / totalCellHeight) - 1

        cellUiList.forEachIndexed { index, uiModel ->
            val schedulePlaceable =
                subcompose("Schedule_$index") {
                    ScheduleCell(
                        modifier = Modifier.fillMaxWidth(),
                        id = uiModel.base.id,
                        type = uiModel.base.type,
                        contentAssignee = uiModel.base.contentAssignee,
                        content = uiModel.base.mainText,
                        onClickCell = onClickCell,
                    )
                }.first().measure(
                    constraints.copy(
                        minHeight = 0,
                        maxHeight = totalCellHeight,
                        minWidth = 0,
                        maxWidth = schedulePerWidth * (uiModel.rowEndIndex - uiModel.rowStartIndex + 1),
                    ),
                )
            if (uiModel.columnIndex <= maxVisibleItemCount) {
                itemsToPlace.add(
                    Triple(
                        schedulePlaceable,
                        uiModel.rowStartIndex * schedulePerWidth,
                        uiModel.columnIndex * totalCellHeight,
                    ),
                )
            } else {
                for (index in uiModel.rowStartIndex..uiModel.rowEndIndex) {
                    outRangeArray[index] += 1
                }
            }
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            itemsToPlace.forEach { (placeable, x, y) ->
                placeable.place(x, y)
            }
            val outRangePlaceable =
                subcompose("OutRange") {
                    ScheduleOutRangeCell(
                        modifier = Modifier,
                        outRange = outRangeArray.toList(),
                    )
                }.firstOrNull()?.measure(
                    constraints.copy(
                        minHeight = 0,
                        maxHeight = totalCellHeight,
                        minWidth = 0,
                        maxWidth = constraints.maxWidth,
                    ),
                )
            val startIndex = outRangeArray.indexOfFirst { it > 0 }
            if (outRangePlaceable != null && startIndex != -1) {
                outRangePlaceable.place(
                    startIndex,
                    (maxVisibleItemCount + 1) * (totalCellHeight),
                )
            }
        }
    }
}

@Composable
private fun ScheduleCell(
    modifier: Modifier = Modifier,
    id: Long,
    type: CalendarUiModel.ScheduleType,
    contentAssignee: ContentAssignee,
    content: String,
    onClickCell: (Long) -> Unit,
) {
    val (backgroundColor, textColor) =
        with(CaramelTheme.color) {
            when (type) {
                CalendarUiModel.ScheduleType.MULTI_SCHEDULE, CalendarUiModel.ScheduleType.SINGLE_SCHEDULE ->
                    when (contentAssignee) {
                        ContentAssignee.ME -> fill.labelAccent3 to text.labelAccent4
                        ContentAssignee.PARTNER -> fill.labelAccent4 to text.labelAccent3
                        ContentAssignee.US -> fill.labelBrand to text.labelBrand
                    }

                CalendarUiModel.ScheduleType.HOLIDAY -> fill.brand to text.inverse
                CalendarUiModel.ScheduleType.ANNIVERSARY -> fill.labelAccent1 to text.inverse
            }
        }
    Box(
        modifier =
            Modifier
                .padding(horizontal = 2.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            modifier =
                modifier
                    .height(height = CalendarDimension.scheduleCellHeight)
                    .background(color = backgroundColor, shape = CaramelTheme.shape.xxs)
                    .padding(vertical = 1.dp, horizontal = 2.dp)
                    .clickable(
                        indication = null,
                        interactionSource = null,
                        onClick = {
                            when (type) {
                                CalendarUiModel.ScheduleType.MULTI_SCHEDULE,
                                CalendarUiModel.ScheduleType.SINGLE_SCHEDULE,
                                -> onClickCell(id)
                                else -> Unit
                            }
                        },
                    ),
            text = content,
            color = textColor,
            style = CaramelTheme.typography.label3.bold,
            textAlign = TextAlign.Start,
            maxLines = 1,
        )
    }
}

@Composable
private fun ScheduleOutRangeCell(
    modifier: Modifier = Modifier,
    outRange: List<Int>,
) {
    if (outRange.all { it == 0 }) return
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(height = CalendarDimension.scheduleCellHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(outRange.size) { index ->
            if (outRange[index] >= 1) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "+${outRange[index]}개",
                    style = CaramelTheme.typography.label3.regular,
                    color = CaramelTheme.color.text.secondary,
                    textAlign = TextAlign.Center,
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
