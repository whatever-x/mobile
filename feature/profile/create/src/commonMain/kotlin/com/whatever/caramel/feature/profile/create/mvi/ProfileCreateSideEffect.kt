package com.whatever.caramel.feature.profile.create.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface ProfileCreateSideEffect : UiSideEffect {

    data object NavigateToLogin : ProfileCreateSideEffect

    data object NavigateToConnectCouple : ProfileCreateSideEffect

}