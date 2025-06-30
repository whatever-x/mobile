package com.whatever.caramel.feature.profile.edit.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface ProfileEditIntent : UiIntent {
    data object ClickCloseButton : ProfileEditIntent

    data object ClickSaveButton : ProfileEditIntent

    data class ChangeNickname(
        val nickname: String,
    ) : ProfileEditIntent

    data class ChangeBirthDayYearPicker(
        val year: Int,
    ) : ProfileEditIntent

    data class ChangeBirthDayMonthPicker(
        val month: Int,
    ) : ProfileEditIntent

    data class ChangeBirthDayDayPicker(
        val day: Int,
    ) : ProfileEditIntent

    data class ChangeDDayYearPicker(
        val year: Int,
    ) : ProfileEditIntent

    data class ChangeDDayMonthPicker(
        val month: Int,
    ) : ProfileEditIntent

    data class ChangeDDayDayPicker(
        val day: Int,
    ) : ProfileEditIntent
}
