package com.whatever.caramel.feature.setting.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface SettingSideEffect : UiSideEffect {

    data object NavigateToHome : SettingSideEffect

    data class NavigateToEditNickname(val nickname: String) : SettingSideEffect

    data class NavigateToEditBirthday(val birthday: String) : SettingSideEffect

    data object NavigateToServiceTermNotion : SettingSideEffect

    data object NavigateToPersonalInfoTermNotion : SettingSideEffect

    data object NavigateLogin : SettingSideEffect

    data class NavigateToEditCountDown(val startDate: String) : SettingSideEffect
}