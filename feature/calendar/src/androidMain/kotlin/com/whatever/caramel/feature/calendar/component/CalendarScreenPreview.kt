package com.whatever.caramel.feature.calendar.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.calendar.CalendarScreen
import com.whatever.caramel.feature.calendar.mvi.CalendarState

@Composable
@Preview
private fun CalendarScreenPreview(
    @PreviewParameter(CalendarScreenPreviewData::class) state : CalendarState
) {
    CaramelTheme {
        CalendarScreen(
            state = state,
            onIntent = {}
        )
    }
}
