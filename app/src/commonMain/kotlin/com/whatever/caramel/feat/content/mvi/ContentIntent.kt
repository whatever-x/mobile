package com.whatever.caramel.feat.content.mvi

import com.whatever.caramel.core.presentation.UiIntent

sealed interface ContentIntent : UiIntent {

    data object ClickDeleteButton : ContentIntent

    data object ClickCloseButton : ContentIntent

    data object ClickSaveButton : ContentIntent

}