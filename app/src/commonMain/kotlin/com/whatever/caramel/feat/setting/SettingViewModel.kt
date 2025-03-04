package com.whatever.caramel.feat.setting

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.presentation.BaseViewModel
import com.whatever.caramel.feat.setting.mvi.SettingIntent
import com.whatever.caramel.feat.setting.mvi.SettingSideEffect
import com.whatever.caramel.feat.setting.mvi.SettingState

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