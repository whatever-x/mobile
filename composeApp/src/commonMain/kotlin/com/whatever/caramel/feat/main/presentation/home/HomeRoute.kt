package com.whatever.caramel.feat.main.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feat.main.presentation.home.mvi.HomeSideEffect
import org.koin.compose.viewmodel.koinViewModel


@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = koinViewModel(),
    navigateToSetting: () -> Unit,
    navigateToStaredCoupleDay: () -> Unit,
    navigateToTodoDetail: () -> Unit,
    navigateToCreateTodo: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.NavigateToSetting -> navigateToSetting()
                is HomeSideEffect.NavigateToStartedCoupleDay -> navigateToStaredCoupleDay()
                is HomeSideEffect.NavigateToCreateTodo -> navigateToCreateTodo()
                is HomeSideEffect.NavigateToTodoDetail -> navigateToTodoDetail()
            }
        }
    }

    HomeScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}