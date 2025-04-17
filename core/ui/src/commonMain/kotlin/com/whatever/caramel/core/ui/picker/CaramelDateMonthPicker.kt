package com.whatever.caramel.core.ui.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.CaramelTextWheelPicker
import com.whatever.caramel.core.designsystem.components.PickerScrollMode.LOOPING
import com.whatever.caramel.core.designsystem.components.rememberPickerState
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.ui.picker.model.DateUiState

@Composable
fun CaramelDateMonthPicker(
    modifier: Modifier = Modifier,
    dateUiState: DateUiState,
    years: List<Int> = (1900..2100).toList(),
    months: List<Int> = (1..12).toList(),
    onYearChanged: (Int) -> Unit,
    onMonthChanged: (Int) -> Unit
) {
    val yearState = rememberPickerState(dateUiState.year)
    val monthState = rememberPickerState(dateUiState.month)

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
    }
}
