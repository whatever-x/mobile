package com.whatever.caramel.feature.content

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.content.mvi.ContentIntent
import com.whatever.caramel.feature.content.mvi.ContentSideEffect
import com.whatever.caramel.feature.content.mvi.ContentState

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
