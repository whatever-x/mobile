package com.whatever.caramel.feat.login.presentation

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.presentation.BaseViewModel
import com.whatever.caramel.feat.login.presentation.mvi.LoginIntent
import com.whatever.caramel.feat.login.presentation.mvi.LoginSideEffect
import com.whatever.caramel.feat.login.presentation.mvi.LoginState

class LoginViewModel(
    savedStateHandle: SavedStateHandle,
    // @ham2174 TODO : 로컬에 사용자 프로필 데이터 유무 로직 구현
) : BaseViewModel<LoginState, LoginSideEffect, LoginIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): LoginState {
        return LoginState()
    }

    override suspend fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.ClickAppleLoginButton -> postSideEffect(LoginSideEffect.NavigateToConnectCouple)
            is LoginIntent.ClickKakaoLoginButton -> postSideEffect(LoginSideEffect.NavigateToCreateProfile)
        }
    }

}