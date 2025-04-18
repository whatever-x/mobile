package com.whatever.caramel.core.ui.list.picker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.ui.picker.CaramelDatePicker
import com.whatever.caramel.core.ui.picker.DateUiState
import io.github.aakira.napier.Napier

@Preview
@Composable
private fun CaramelDatePickerPreview() {
    CaramelTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CaramelDatePicker(
                dateUiState = DateUiState.currentDate(),
                onYearChanged = { year -> Napier.d { year.toString() } },
                onMonthChanged = { month -> Napier.d { month.toString() } },
                onDayChanged = { day -> Napier.d { day.toString() } },
            )
        }
    }
}