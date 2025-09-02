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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.feature.calendar.dimension.CalendarDimension
import com.whatever.caramel.feature.calendar.mvi.CalendarScheduleType
import com.whatever.caramel.feature.calendar.mvi.ScheduleUiModel
import io.github.aakira.napier.Napier

@Composable
fun CalendarScheduleList(
    modifier: Modifier = Modifier,
    uiModelList: List<ScheduleUiModel>,
    onClickSchedule: (Long) -> Unit,
) {
    // 모든 데이터의 기준은 픽셀로 설정한다.
    val density = LocalDensity.current
    val spacingBetweenItemsPx = with(density) { CaramelTheme.spacing.xxs.roundToPx() }
    val scheduleCellHeightPx = with(density) { CalendarDimension.scheduleCellHeight.roundToPx() }
    val totalCellHeight = scheduleCellHeightPx + spacingBetweenItemsPx
    SubcomposeLayout(modifier = modifier) { constraints ->
        val parentHeight = constraints.maxHeight
        val schedulePerWidth =
            (constraints.maxWidth / 7)  // 한칸에 들어갈 수 있는 width의 크기
        val itemsToPlace = mutableListOf<Triple<Placeable, Int, Int>>() // subCompose + X좌표 + Y좌표
        val outRangeArray = IntArray(7)
        val maxVisibleItemCount = (parentHeight / totalCellHeight) - 1   // 배치 가능 아이템, 1개는 +N개용

        // 해당 주차의 일정을 작성해준다
        uiModelList.forEachIndexed { index, uiModel ->
            // 일정을 그려준다.
            val schedulePlaceable = subcompose("Schedule_$index") {
                // 셀의 가로 길이를 제공해준다
                ScheduleCell(
                    modifier = Modifier.fillMaxWidth(),
                    uiModel = uiModel
                )
            }.first().measure(
                constraints.copy(
                    minHeight = 0,
                    maxHeight = totalCellHeight,
                    minWidth = 0,
                    maxWidth = schedulePerWidth * (uiModel.rowEndIndex - uiModel.rowStartIndex + 1)
                )
            )
            if (uiModel.columnIndex <= maxVisibleItemCount) { // 스케쥴로 배치가 가능
                itemsToPlace.add(
                    Triple(
                        schedulePlaceable,
                        uiModel.rowStartIndex * schedulePerWidth,
                        uiModel.columnIndex * totalCellHeight
                    )
                )
            } else {    // 배치 불가능, +N개가 배치되어야하는 UI 제공
                for (index in uiModel.rowStartIndex..uiModel.rowEndIndex) {
                    outRangeArray[index] += 1
                }
            }
        }

        // 실제 배치
        layout(constraints.maxWidth, constraints.maxHeight) {
            itemsToPlace.forEach { (placeable, x, y) ->
                placeable.place(x, y)
            }
            val outRangePlaceable = subcompose("OutRange") {
                ScheduleOutRangeCell(
                    modifier = Modifier,
                    outRange = outRangeArray
                )
            }.first().measure(
                constraints.copy(
                    minHeight = 0,
                    maxHeight = totalCellHeight,
                    minWidth = 0,
                    maxWidth = constraints.maxWidth
                )
            )
            val startIndex = outRangeArray.indexOfFirst { it > 0 }
            if (startIndex != -1) {
                outRangePlaceable.place(
                    startIndex,
                    (maxVisibleItemCount + 1) * (totalCellHeight)
                )
            }
        }
    }
}

@Composable
private fun ScheduleCell(
    modifier: Modifier = Modifier,
    uiModel: ScheduleUiModel,
) {
    val (backgroundColor, textColor) = with(CaramelTheme.color) {
        when (uiModel.type) {
            CalendarScheduleType.MULTI_SCHEDULE, CalendarScheduleType.SINGLE_SCHEDULE -> when (uiModel.contentAssignee) {
                ContentAssignee.ME -> fill.labelAccent3 to text.labelAccent4
                ContentAssignee.PARTNER -> fill.labelAccent4 to text.labelAccent3
                ContentAssignee.US, null -> fill.labelBrand to text.labelBrand
            }

            CalendarScheduleType.HOLIDAY -> fill.brand to text.inverse
            CalendarScheduleType.ANNIVERSARY -> fill.labelAccent1 to text.inverse
        }
    }
    Box(modifier = Modifier.padding(horizontal = 2.dp)) {
        Text(
            modifier = modifier
                .height(height = CalendarDimension.scheduleCellHeight)
                .background(color = backgroundColor, shape = CaramelTheme.shape.xxs)
                .padding(vertical = 1.dp, horizontal = 2.dp)
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = {}
                )
                .align(Alignment.Center),
            text = uiModel.mainText,
            color = textColor,
            style = CaramelTheme.typography.label3.bold,
            textAlign = TextAlign.Start,
        )
    }
}

@Composable
private fun ScheduleOutRangeCell(
    modifier: Modifier = Modifier,
    outRange: IntArray
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height = CalendarDimension.scheduleCellHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(outRange.size) { index ->
            if (outRange[index] >= 1) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "+${outRange[index]}개",
                    style = CaramelTheme.typography.label3.regular,
                    color = CaramelTheme.color.text.secondary,
                    textAlign = TextAlign.Center
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}