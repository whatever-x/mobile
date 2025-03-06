package com.whatever.caramel.feature.profile.edit.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface ProfileEditIntent : UiIntent {

    data object ClickCloseButton : ProfileEditIntent

    data object ClickSaveButton : ProfileEditIntent

}