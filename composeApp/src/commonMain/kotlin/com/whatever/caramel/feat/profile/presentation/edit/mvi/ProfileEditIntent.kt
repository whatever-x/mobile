package com.whatever.caramel.feat.profile.presentation.edit.mvi

import com.whatever.caramel.core.presentation.UiIntent

sealed interface ProfileEditIntent : UiIntent {

    data object ClickCloseButton : ProfileEditIntent

    data object ClickSaveButton : ProfileEditIntent

}