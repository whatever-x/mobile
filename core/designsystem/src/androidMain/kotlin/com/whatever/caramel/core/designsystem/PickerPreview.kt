package com.whatever.caramel.core.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.CaramelTextWheelPicker
import com.whatever.caramel.core.designsystem.components.PickerScrollMode
import com.whatever.caramel.core.designsystem.components.rememberPickerState
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Preview(showBackground = true)
@Composable
private fun CaramelWheelPickerPreview() {
    CaramelTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val items = List(1002) { it.toString() }

            CaramelTextWheelPicker(
                modifier = Modifier.width(50.dp),
                items = items,
                itemSpacing = 8.dp,
                state = rememberPickerState(items),
                scrollMode = PickerScrollMode.LOOPING,
                onHapticFeedback = { }
            )
        }
    }
}