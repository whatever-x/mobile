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
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class DateUiState(
    val year: Int,
    val month: Int,
    val day: Int
) {
    companion object {
        fun today(): DateUiState {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

            return DateUiState(
                year = now.year,
                month = now.monthNumber,
                day = now.dayOfMonth
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
            val lastDay = getLastDayOfMonth(yearState.selectedItem, monthState.selectedItem)
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

fun getLastDayOfMonth(year: Int, month: Int): Int = // 년, 월에 따른 day 리스트 구하는 함수
    when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> 30
    }

fun isLeapYear(year: Int): Boolean { // 윤년 여부 함수
    return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0
}