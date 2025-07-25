package com.whatever.caramel.feature.calendar.component

import android.R.attr.description
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.calendar.CalendarScreen
import com.whatever.caramel.feature.calendar.component.bottomSheet.BottomSheetTodoItem
import com.whatever.caramel.feature.calendar.component.bottomSheet.CaramelDefaultBottomTodoScope
import com.whatever.caramel.feature.calendar.component.bottomSheet.DefaultBottomSheetTodoItem
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
    @PreviewParameter(CalendarBottomSheetPreviewData::class) state: CaramelDefaultBottomTodoScope,
) {
    CaramelTheme {
        BottomSheetTodoItem(
            id = state.id,
            title = state.title,
            description = state.description,
            url = state.url,
            role = state.role,
            onClickTodo = state.onClickTodo,
            onClickUrl = state.onClickUrl,
        ) {
            DefaultBottomSheetTodoItem()
        }
    }
}
