package com.whatever.caramel.feature.calendar.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.util.DateUtil
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import kotlin.math.ceil

@Composable
internal fun MonthCalendar(
    modifier: Modifier = Modifier,
    year: Int,
    month: Int
) {
    SubcomposeLayout(modifier = modifier) { constraints ->
        val firstDay = LocalDate(year, month, 1)
        val daysInMonth = DateUtil.getLastDayOfMonth(
            year = year,
            month = month
        )
        val startDayOfWeek = firstDay.dayOfWeek.isoDayNumber % 7
        val totalCells = startDayOfWeek + daysInMonth
        val weekCount = ceil(totalCells / 7f).toInt()

        val cellWidth = constraints.maxWidth / 7
        val cellHeight = constraints.maxHeight / weekCount

        val placeableList = (0 until totalCells).map { index ->
            val day = index - startDayOfWeek + 1
            val date =
                if (day in 1..daysInMonth) LocalDate(year, month, day)
                else null

            subcompose("cell_$index") {
                val density = LocalDensity.current
                val cellWidthDp = with(density) { cellWidth.toDp() }
                val cellHeightDp = with(density) { cellHeight.toDp() }

                Box(
                    modifier = Modifier
                        .size(
                            width = cellWidthDp,
                            height = cellHeightDp
                        )
                        .border(0.5.dp, Color.LightGray)
                ) {
                    if (date != null) {
                        Text(
                            text = day.toString(),
                            modifier = Modifier.align(Alignment.TopStart)
                        )
                    }
                }
            }.first().measure(Constraints.fixed(cellWidth, cellHeight))
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeableList.forEachIndexed { index, p ->
                val row = index / 7
                val col = index % 7
                p.place(col * cellWidth, row * cellHeight)
            }
        }
    }
}
