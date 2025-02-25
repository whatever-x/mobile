package com.whatever.caramel.feat.profile.presentation.create.mvi

import com.whatever.caramel.core.presentation.UiIntent

sealed interface ProfileCreateIntent : UiIntent {

    data object ClickBackButton : ProfileCreateIntent

    data object ClickCreateButton : ProfileCreateIntent

}