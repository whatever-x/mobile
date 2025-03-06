package com.whatever.caramel.feature.login.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface LoginSideEffect : UiSideEffect {

    data object NavigateToCreateProfile : LoginSideEffect

    data object NavigateToConnectCouple : LoginSideEffect

}