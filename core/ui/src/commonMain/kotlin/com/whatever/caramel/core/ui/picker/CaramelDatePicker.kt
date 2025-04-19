package com.whatever.caramel.core.ui.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.CaramelTextWheelPicker
import com.whatever.caramel.core.designsystem.components.PickerScrollMode.BOUNDED
import com.whatever.caramel.core.designsystem.components.PickerScrollMode.LOOPING
import com.whatever.caramel.core.designsystem.components.rememberPickerState
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.util.DateFormatter.toLocalDateTime
import com.whatever.caramel.core.util.DateUtil

data class DateUiState(
    val year: Int,
    val month: Int,
    val day: Int
) {
    companion object {
        fun currentDate(): DateUiState {
            val today = DateUtil.today()

            return DateUiState(
                year = today.year,
                month = today.monthNumber,
                day = today.dayOfMonth
            )
        }

        fun get(millisecond: Long) : DateUiState {
            val localDateTime = millisecond.toLocalDateTime()
            return DateUiState(
                year = localDateTime.year,
                month = localDateTime.monthNumber,
                day = localDateTime.dayOfMonth
            )
        }
    }
}

@Composable
fun CaramelDatePicker(
    modifier: Modifier = Modifier,
    dateUiState: DateUiState,
    years: List<Int> = (1900..2100).toList(),
    months: List<Int> = (1..12).toList(),
    onYearChanged: (Int) -> Unit,
    onMonthChanged: (Int) -> Unit,
    onDayChanged: (Int) -> Unit,
) {
    val yearState = rememberPickerState(dateUiState.year)
    val monthState = rememberPickerState(dateUiState.month)
    val dayState = rememberPickerState(dateUiState.day)

    val days by remember(yearState.selectedItem, monthState.selectedItem) {
        derivedStateOf {
            val lastDay =
                DateUtil.getLastDayOfMonth(yearState.selectedItem, monthState.selectedItem)
            (1..lastDay).toList()
        }
    }

    Row(
        modifier = modifier
            .padding(
                vertical = CaramelTheme.spacing.m,
                horizontal = CaramelTheme.spacing.xl
            ),
        horizontalArrangement = Arrangement.spacedBy(
            space = 40.dp
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CaramelTextWheelPicker(
            items = years,
            state = yearState,
            dividerWidth = 50.dp,
            scrollMode = LOOPING,
            onItemSelected = { year -> onYearChanged(year) }
        )

        CaramelTextWheelPicker(
            items = months,
            state = monthState,
            dividerWidth = 50.dp,
            scrollMode = LOOPING,
            onItemSelected = { month -> onMonthChanged(month) }
        )

        CaramelTextWheelPicker(
            items = days,
            state = dayState,
            dividerWidth = 60.dp,
            scrollMode = BOUNDED, // @ham2174 FIXME : MVP 이후 day 피커 LOOPING 모드로 수정
            onItemSelected = { day -> onDayChanged(day) }
        )
    }
}
