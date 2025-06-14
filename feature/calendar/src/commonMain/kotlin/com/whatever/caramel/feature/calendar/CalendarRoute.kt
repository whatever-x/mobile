package com.whatever.caramel.feature.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.feature.calendar.mvi.CalendarSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CalendarRoute(
    viewModel: CalendarViewModel = koinViewModel(),
    navigateToCreateTodo: (ContentType, String) -> Unit,
    navigateToTodoDetail: (Long, ContentType) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CalendarSideEffect.NavigateToTodoDetail -> navigateToTodoDetail(sideEffect.id, sideEffect.contentType)
                is CalendarSideEffect.NavigateToAddSchedule -> navigateToCreateTodo(ContentType.CALENDAR, sideEffect.dateTimeString)
                is CalendarSideEffect.OpenWebView -> uriHandler.openUri(sideEffect.url)
            }
        }
    }

    CalendarScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}