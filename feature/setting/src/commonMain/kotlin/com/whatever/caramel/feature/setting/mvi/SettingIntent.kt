package com.whatever.caramel.feature.setting.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface SettingIntent : UiIntent {

    data object ClickEditCountDownButton : SettingIntent

    data object ClickSettingBackButton : SettingIntent

    data object ToggleEditProfile : SettingIntent

    data object ClickEditNicknameButton : SettingIntent

    data object ClickEditBirthDayButton : SettingIntent

    data object ToggleLogout : SettingIntent

    data object ClickTermsOfServiceButtons : SettingIntent

    data object ClickAppUpdateButton : SettingIntent

    data object ClickPrivacyPolicyButton : SettingIntent

    data object ClickLogoutConfirmButton : SettingIntent

    data object ToggleUserCancelledButton : SettingIntent
}