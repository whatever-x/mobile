package com.whatever.caramel.feature.couple.connect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CoupleConnectRoute(
    viewModel: CoupleConnectViewModel = koinViewModel(),
    navigateToMain: () -> Unit,
    navigateToInviteCouple: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CoupleConnectSideEffect.NavigateToMain -> navigateToMain()
                is CoupleConnectSideEffect.NavigateToInviteCouple -> navigateToInviteCouple()
            }
        }
    }

    CoupleConnectScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}