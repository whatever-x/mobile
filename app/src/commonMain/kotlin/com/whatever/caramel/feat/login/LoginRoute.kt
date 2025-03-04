package com.whatever.caramel.feat.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feat.login.mvi.LoginSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel = koinViewModel(),
    navigateToCreateProfile: () -> Unit,
    navigateToConnectCouple: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.NavigateToCreateProfile -> navigateToCreateProfile()
                is LoginSideEffect.NavigateToConnectCouple -> navigateToConnectCouple()
            }
        }
    }

    LoginScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}