package com.whatever.caramel.feature.setting.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface SettingIntent : UiIntent {

    data object ClickBackButton : SettingIntent

    data object ClickEditNicknameButton : SettingIntent

    data object ClickEditBirthdayButton : SettingIntent

}