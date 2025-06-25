package com.whatever.caramel.feature.profile.edit.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface ProfileEditSideEffect : UiSideEffect {

    data object PopBackStack : ProfileEditSideEffect

    data object PerformHapticFeedback : ProfileEditSideEffect

    data class ShowErrorDialog(val message : String, val description : String?) : ProfileEditSideEffect

    data class ShowErrorToast(val message : String) : ProfileEditSideEffect

}