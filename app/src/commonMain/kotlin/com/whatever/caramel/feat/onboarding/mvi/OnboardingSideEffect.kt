package com.whatever.caramel.feat.onboarding.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface OnboardingSideEffect : UiSideEffect {

    data object NavigateToLogin : OnboardingSideEffect

}