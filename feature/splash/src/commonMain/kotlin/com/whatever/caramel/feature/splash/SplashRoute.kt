package com.whatever.caramel.feature.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.feature.splash.mvi.SplashSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SplashRoute(
    viewModel: SplashViewModel = koinViewModel(),
    navigateToStartDestination: (UserStatus) -> Unit,
    navigateToLogin: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SplashSideEffect.NavigateToLogin -> navigateToLogin()
                is SplashSideEffect.NavigateToStartDestination -> navigateToStartDestination(sideEffect.userStatus)
            }
        }
    }

    SplashScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) },
    )
}
