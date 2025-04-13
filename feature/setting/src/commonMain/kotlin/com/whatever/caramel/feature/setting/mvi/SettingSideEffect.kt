package com.whatever.caramel.feature.setting.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface SettingSideEffect : UiSideEffect {

    data object NavigateToHome : SettingSideEffect

    data object NavigateToEditNickName : SettingSideEffect

    data object NavigateToEditBirthday : SettingSideEffect
}