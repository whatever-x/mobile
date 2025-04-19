package com.whatever.caramel.core.ui.list.picker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.ui.picker.CaramelTimePicker
import com.whatever.caramel.core.ui.picker.TimeUiState
import io.github.aakira.napier.Napier

@Preview
@Composable
private fun CaramelTimePickerPreview() {
    CaramelTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CaramelTimePicker(
                timeUiState = TimeUiState.default(),
                onMinuteChanged = { minute -> Napier.d { "minute changed: $minute" }},
                onHourChanged = { hour -> Napier.d { "hour changed: $hour" } },
                onPeriodChanged = { period -> Napier.d { "period changed: $period" } },
            )
        }
    }
}