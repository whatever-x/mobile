package com.whatever.caramel.feature.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feature.calendar.mvi.CalendarSideEffect
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CalendarRoute(
    viewModel: CalendarViewModel = koinViewModel(),
    navigateToCreateTodo: () -> Unit,
    navigateToTodoDetail: () -> Unit,
) {
    val current = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    viewModel.loadCalendar(current.year, current.month.ordinal + 1)

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