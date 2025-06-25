package com.whatever.caramel.feature.copule.connecting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feature.copule.connecting.mvi.CoupleConnectingSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CoupleConnectingRoute(
    viewModel: CoupleConnectingViewModel = koinViewModel(),
    navigateToMain: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CoupleConnectingSideEffect.NavigateToMain -> navigateToMain()
            }
        }
    }

    CoupleConnectingScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}