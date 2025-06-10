package com.whatever.caramel.feature.couple.connect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.designsystem.components.LocalSnackbarHostState
import com.whatever.caramel.core.designsystem.components.showSnackbarMessage
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CoupleConnectRoute(
    viewModel: CoupleConnectViewModel = koinViewModel(),
    navigateToMain: () -> Unit,
    navigateToInviteCouple: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackBarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CoupleConnectSideEffect.NavigateToMain -> navigateToMain()
                is CoupleConnectSideEffect.NavigateToInviteCouple -> navigateToInviteCouple()
                is CoupleConnectSideEffect.ShowSnackBarMessage -> {
                    showSnackbarMessage(
                        coroutineScope = scope,
                        message = sideEffect.message,
                        snackbarHostState = snackBarHostState
                    )
                }
            }
        }
    }

    CoupleConnectScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}