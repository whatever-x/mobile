package com.whatever.caramel.feature.content.mvi

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface ContentIntent : UiIntent {

    data object ClickDeleteButton : ContentIntent

    data object ClickCloseButton : ContentIntent

    data object ClickSaveButton : ContentIntent

    data class InputTitle(val text: String) : ContentIntent

    data class InputContent(val text: String) : ContentIntent

    data class ClickTag(val tag: Tag) : ContentIntent

    data class SelectCreateMode(val createMode: ContentState.CreateMode) : ContentIntent

    data class RecognizeLink(val link: String) : ContentIntent
}