package com.whatever.caramel.feat.profile.presentation.create.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface ProfileCreateSideEffect : UiSideEffect {

    data object NavigateToLogin : ProfileCreateSideEffect

    data object NavigateToConnectCouple : ProfileCreateSideEffect

}