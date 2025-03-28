package com.whatever.caramel.feature.profile.create.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface ProfileCreateIntent : UiIntent {

    data object ClickBackButton : ProfileCreateIntent

    data object ClickNextButton : ProfileCreateIntent

    data class ChangeNickname(val nickname: String) : ProfileCreateIntent

    data class ClickGenderButton(val gender: Gender) : ProfileCreateIntent

    data object ClickSystemNavigationBackButton : ProfileCreateIntent

    data object ToggleServiceTerm : ProfileCreateIntent

    data object TogglePersonalInfoTerm : ProfileCreateIntent

    data object ClickServiceTermLabel : ProfileCreateIntent

    data object ClickPersonalInfoTermLabel : ProfileCreateIntent

}