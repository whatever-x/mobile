package com.whatever.caramel.feat.setting.mvi

import com.whatever.caramel.core.presentation.UiIntent

sealed interface SettingIntent : UiIntent {

    data object ClickBackButton : SettingIntent

    data object ClickEditNicknameButton : SettingIntent

    data object ClickEditBirthdayButton : SettingIntent

}