package com.whatever.caramel.feat.profile.presentation.edit.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface ProfileEditSideEffect : UiSideEffect {

    data object PopBackStack : ProfileEditSideEffect

}