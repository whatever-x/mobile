package com.whatever.caramel.feat.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feat.onboarding.mvi.OnboardingSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun OnboardingRoute(
    viewModel: OnboardingViewModel = koinViewModel(),
    navigateToLogin: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is OnboardingSideEffect.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    OnboardingScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}