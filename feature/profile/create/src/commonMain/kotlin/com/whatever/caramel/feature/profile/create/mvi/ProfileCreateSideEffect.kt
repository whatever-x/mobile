package com.whatever.caramel.feature.profile.create.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface ProfileCreateSideEffect : UiSideEffect {

    data object NavigateToLogin : ProfileCreateSideEffect

    data object NavigateToConnectCouple : ProfileCreateSideEffect

    data object NavigateToServiceTermNotion : ProfileCreateSideEffect

    data object NavigateToPersonalInfoTermNotion : ProfileCreateSideEffect

    data object PerformHapticFeedback : ProfileCreateSideEffect

    data class ShowErrorSnackBar(val message: String) : ProfileCreateSideEffect

}