package com.whatever.caramel.feat.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feat.splash.mvi.SplashSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SplashRoute(
    viewModel: SplashViewModel = koinViewModel(),
    navigateToOnBoarding: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SplashSideEffect.NavigateToOnBoarding -> navigateToOnBoarding()
                is SplashSideEffect.NavigateToLogin -> navigateToLogin()
                is SplashSideEffect.NavigateToMain -> navigateToMain()
            }
        }
    }

    SplashScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}