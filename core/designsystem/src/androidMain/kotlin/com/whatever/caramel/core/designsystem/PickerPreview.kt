package com.whatever.caramel.core.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
            contentAlignment = Alignment.Center,
        ) {
            val items = listOf("갉", "나", "다", "라", "마", "바", "사", "아", "자", "차", "카", "타", "파", "하")

            CaramelTextWheelPicker(
                items = items,
                state = rememberPickerState("나"),
                scrollMode = PickerScrollMode.LOOPING,
                onItemSelected = {},
            )
        }
    }
}
