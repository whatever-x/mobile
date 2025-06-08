package com.whatever.caramel.feature.profile.edit.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface ProfileEditSideEffect : UiSideEffect {

    data object PopBackStack : ProfileEditSideEffect

    data object PerformHapticFeedback : ProfileEditSideEffect

}