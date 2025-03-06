package com.whatever.caramel.feature.profile.create.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface ProfileCreateIntent : UiIntent {

    data object ClickBackButton : ProfileCreateIntent

    data object ClickCreateButton : ProfileCreateIntent

}