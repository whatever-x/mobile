package com.whatever.caramel.feature.setting.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface SettingSideEffect : UiSideEffect {

    data object NavigateToHome : SettingSideEffect

    data object NavigateToEditNickname : SettingSideEffect

    data object NavigateToEditBirthDay : SettingSideEffect

    data object NavigateToServiceTermNotion : SettingSideEffect

    data object NavigateToPersonalInfoTermNotion : SettingSideEffect

    data object NavigateLogin : SettingSideEffect

    data class NavigateToEditCountDown(val startDateMillisecond : Long) : SettingSideEffect
}