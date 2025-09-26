package com.whatever.caramel.core.ui.picker

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.CaramelTextWheelPicker
import com.whatever.caramel.core.designsystem.components.PickerScrollMode.BOUNDED
import com.whatever.caramel.core.designsystem.components.PickerScrollMode.LOOPING
import com.whatever.caramel.core.designsystem.components.rememberPickerState
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.ui.picker.model.Period
import com.whatever.caramel.core.ui.picker.model.TimeUiState

@Composable
fun CaramelTimePicker(
    modifier: Modifier = Modifier,
    timeUiState: TimeUiState,
    hour: List<String> = (0..11).toList().map { it.toString() },
    minute: List<String> = (0..59 step 5).map { it.toString().padStart(2, '0') },
    onPeriodChanged: (String) -> Unit,
    onHourChanged: (String) -> Unit,
    onMinuteChanged: (String) -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    val periodState = rememberPickerState(initialItem = timeUiState.period)
    val hourState = rememberPickerState(initialItem = timeUiState.hour)
    val minuteState = rememberPickerState(initialItem = timeUiState.minute)

    Row(
        modifier =
            modifier
                .padding(
                    vertical = CaramelTheme.spacing.m,
                    horizontal = CaramelTheme.spacing.xl,
                ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CaramelTextWheelPicker(
            items = Period.entries.map { it.value },
            state = periodState,
            dividerWidth = 50.dp,
            scrollMode = BOUNDED,
            onItemSelected = { period ->
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                onPeriodChanged(period)
            },
        )

        Spacer(modifier = Modifier.width(width = 40.dp))

        CaramelTextWheelPicker(
            items = hour,
            state = hourState,
            dividerWidth = 50.dp,
            scrollMode = LOOPING,
            onItemSelected = { hour ->
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                onHourChanged(hour)
            },
        )

        Spacer(modifier = Modifier.width(width = 20.dp))

        Text(
            text = ":",
            style = CaramelTheme.typography.heading2,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.width(width = 20.dp))

        CaramelTextWheelPicker(
            items = minute,
            state = minuteState,
            dividerWidth = 60.dp,
            scrollMode = LOOPING,
            onItemSelected = { minute ->
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                onMinuteChanged(minute)
            },
        )
    }
}
