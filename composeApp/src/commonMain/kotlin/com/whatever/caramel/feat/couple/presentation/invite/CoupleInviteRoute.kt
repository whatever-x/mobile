package com.whatever.caramel.feat.couple.presentation.invite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feat.couple.presentation.invite.mvi.CoupleInviteSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CoupleInviteRoute(
    viewModel: CoupleInviteViewModel = koinViewModel(),
    navigateToCoupleCode: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CoupleInviteSideEffect.NavigateToCoupleCode -> navigateToCoupleCode()
            }
        }
    }

    CoupleInviteScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}