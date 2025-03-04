package com.whatever.caramel.feat.content

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.presentation.BaseViewModel
import com.whatever.caramel.feat.content.mvi.ContentIntent
import com.whatever.caramel.feat.content.mvi.ContentSideEffect
import com.whatever.caramel.feat.content.mvi.ContentState

class ContentViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ContentState, ContentSideEffect, ContentIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): ContentState {
        return ContentState()
    }

    override suspend fun handleIntent(intent: ContentIntent) {
        when (intent) {
            is ContentIntent.ClickCloseButton -> postSideEffect(ContentSideEffect.NavigateToBackStack)
            is ContentIntent.ClickSaveButton -> postSideEffect(ContentSideEffect.NavigateToBackStack)
            is ContentIntent.ClickDeleteButton -> postSideEffect(ContentSideEffect.NavigateToBackStack)
        }
    }

}
