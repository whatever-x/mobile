package com.whatever.caramel.feature.content.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface ContentIntent : UiIntent {

    data object ClickDeleteButton : ContentIntent

    data object ClickCloseButton : ContentIntent

    data object ClickSaveButton : ContentIntent

}