package com.whatever.caramel.feat.main.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feat.main.calendar.mvi.CalendarSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CalendarRoute(
    viewModel: CalendarViewModel = koinViewModel(),
    navigateToCreateTodo: () -> Unit,
    navigateToTodoDetail: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CalendarSideEffect.NavigateToCreateTodo -> navigateToCreateTodo()
                is CalendarSideEffect.NavigateToTodoDetail -> navigateToTodoDetail()
            }
        }
    }

    CalendarScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}