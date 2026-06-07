package com.whatever.caramel.feature.balancegame.share

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BalanceGameShareRoute(
    viewModel: BalanceGameShareViewModel = koinViewModel(),
    navigateToBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is BalanceGameShareSideEffect.NavigateToBack -> navigateToBack()
            }
        }
    }

    BalanceGameShareScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) },
    )
}
