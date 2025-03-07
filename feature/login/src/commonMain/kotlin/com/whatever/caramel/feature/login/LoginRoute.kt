package com.whatever.caramel.feature.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feature.login.mvi.LoginIntent
import com.whatever.caramel.feature.login.mvi.LoginSideEffect
import com.whatever.caramel.feature.login.mvi.SocialAuthType
import com.whatever.caramel.feature.login.social.kakao.KakaoAuthProvider
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel = koinViewModel(),
    navigateToCreateProfile: () -> Unit,
    navigateToConnectCouple: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val kakaoAuthenticator = koinInject<KakaoAuthProvider>().get()

    val socialAuthLaunch: (SocialAuthType) -> Unit = remember {
        { type ->
            scope.launch {
                when (type) {
                    SocialAuthType.KAKAO -> {
                        val result = kakaoAuthenticator.authenticate()
                        viewModel.intent(LoginIntent.ClickKakaoLoginButton(result = result))
                    }

                    SocialAuthType.APPLE -> {

                    }
                }
            }
        }
    }

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
        onIntent = { intent -> viewModel.intent(intent) },
        onLaunch = { socialType -> socialAuthLaunch(socialType) }
    )
}