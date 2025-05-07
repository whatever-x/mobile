package com.whatever.caramel.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface AppSideEffect : UiSideEffect {

    data object NavigateToConnectingCoupleScreen : AppSideEffect

    data object NavigateToInviteCoupleScreen : AppSideEffect

    data object NavigateToLogin : AppSideEffect

    data object NavigateToCreateProfile : AppSideEffect

    data object NavigateToMain : AppSideEffect

}