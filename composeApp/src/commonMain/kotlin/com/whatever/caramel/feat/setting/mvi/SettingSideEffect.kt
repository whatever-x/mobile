package com.whatever.caramel.feat.setting.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface SettingSideEffect : UiSideEffect {

    data object NavigateToHome : SettingSideEffect

    data object NavigateToEditNickName : SettingSideEffect

    data object NavigateToEditBirthday : SettingSideEffect

}