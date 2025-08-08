package com.whatever.caramel.feature.calendar.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.calendar.CalendarScreen
import com.whatever.caramel.feature.calendar.component.bottomSheet.BottomSheetScheduleItem
import com.whatever.caramel.feature.calendar.component.bottomSheet.CaramelDefaultBottomSheetScheduleScope
import com.whatever.caramel.feature.calendar.component.bottomSheet.DefaultBottomSheetScheduleItem
import com.whatever.caramel.feature.calendar.mvi.CalendarState

@Composable
@Preview
private fun CalendarScreenPreview(
    @PreviewParameter(CalendarScreenPreviewData::class) state: CalendarState,
) {
    CaramelTheme {
        CalendarScreen(
            state = state,
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun CalendarBottomSheetPreview(
    @PreviewParameter(CalendarBottomSheetPreviewData::class) state: CaramelDefaultBottomSheetScheduleScope,
) {
    CaramelTheme {
        BottomSheetScheduleItem(
            id = state.id,
            title = state.title,
            description = state.description,
            url = state.url,
            contentAssignee = state.contentAssignee,
            onClickSchedule = state.onClickSchedule,
            onClickUrl = state.onClickUrl,
        ) {
            DefaultBottomSheetScheduleItem()
        }
    }
}
