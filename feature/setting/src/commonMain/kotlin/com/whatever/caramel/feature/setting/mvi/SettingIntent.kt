package com.whatever.caramel.feature.setting.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface SettingIntent : UiIntent {

    data object ClickSettingBackButton : SettingIntent

    data object ClickEditNicknameButton : SettingIntent

    data object ClickEditBirthdayButton : SettingIntent

    data object ClickLogoutButton : SettingIntent

    data object ClickCancelButton : SettingIntent

    data object ClickTermsOfServiceButtons : SettingIntent

    data object ClickPrivacyPolicyButton : SettingIntent
}