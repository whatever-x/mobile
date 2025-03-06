package com.whatever.caramel.feature.setting

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.setting.mvi.SettingIntent
import com.whatever.caramel.feature.setting.mvi.SettingSideEffect
import com.whatever.caramel.feature.setting.mvi.SettingState

class SettingViewModel(
    savedStateHandle: SavedStateHandle
): BaseViewModel<SettingState, SettingSideEffect, SettingIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): SettingState {
        return SettingState()
    }

    override suspend fun handleIntent(intent: SettingIntent) {
        when (intent) {
            is SettingIntent.ClickBackButton -> postSideEffect(SettingSideEffect.NavigateToHome)
            is SettingIntent.ClickEditBirthdayButton -> postSideEffect(SettingSideEffect.NavigateToEditBirthday)
            is SettingIntent.ClickEditNicknameButton -> postSideEffect(SettingSideEffect.NavigateToEditNickName)
        }
    }

}