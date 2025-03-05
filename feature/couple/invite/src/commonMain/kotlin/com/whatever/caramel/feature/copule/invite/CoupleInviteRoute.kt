package com.whatever.caramel.feature.copule.invite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feature.copule.invite.mvi.CoupleInviteSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CoupleInviteRoute(
    viewModel: CoupleInviteViewModel = koinViewModel(),
    navigateToConnectCouple: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CoupleInviteSideEffect.NavigateToConnectCouple -> navigateToConnectCouple()
                is CoupleInviteSideEffect.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    CoupleInviteScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}