package com.whatever.caramel.feature.onboarding.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface OnboardingSideEffect : UiSideEffect {

    data object NavigateToLogin : OnboardingSideEffect

}