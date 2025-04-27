package com.whatever.caramel.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feature.home.mvi.HomeSideEffect
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
                is HomeSideEffect.NavigateToCreateContent -> navigateToCreateTodo()
                is HomeSideEffect.NavigateToContentDetail -> navigateToTodoDetail()
                is HomeSideEffect.NavigateToEditAnniversary -> navigateToStaredCoupleDay()
            }
        }
    }

    HomeScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}