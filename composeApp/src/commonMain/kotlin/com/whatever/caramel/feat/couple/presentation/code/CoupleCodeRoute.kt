package com.whatever.caramel.feat.couple.presentation.code

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feat.couple.presentation.code.mvi.CoupleCodeSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CoupleCodeRoute(
    viewModel: CoupleCodeViewModel = koinViewModel(),
    navigateToHome: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CoupleCodeSideEffect.NavigateToHome -> navigateToHome()
            }
        }
    }

    CoupleCodeScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}