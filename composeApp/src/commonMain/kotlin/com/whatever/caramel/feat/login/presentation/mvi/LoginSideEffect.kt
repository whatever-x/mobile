package com.whatever.caramel.feat.login.presentation.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface LoginSideEffect : UiSideEffect {

    data object NavigateToCreateProfile : LoginSideEffect

    data object NavigateToConnectCouple : LoginSideEffect

}