package com.whatever.caramel.feature.balancegame.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistorySideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BalanceGameHistoryRoute(
    viewModel: BalanceGameHistoryViewModel = koinViewModel(),
    navigateToBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is BalanceGameHistorySideEffect.NavigateToBack -> navigateToBack()
                is BalanceGameHistorySideEffect.NavigateToShare -> Unit
            }
        }
    }

    BalanceGameHistoryScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) },
    )
}
