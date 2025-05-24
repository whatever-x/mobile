package com.whatever.caramel.feature.content

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.content.mvi.ContentIntent
import com.whatever.caramel.feature.content.mvi.ContentSideEffect
import com.whatever.caramel.feature.content.mvi.ContentState
import com.whatever.caramel.feature.content.navigation.ContentRoute
import io.github.aakira.napier.Napier

class ContentViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ContentState, ContentSideEffect, ContentIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): ContentState {
        val arguments = savedStateHandle.toRoute<ContentRoute>()
        return ContentState(
            id = arguments.contentId
        )
    }

    override suspend fun handleIntent(intent: ContentIntent) {
        when (intent) {
            is ContentIntent.ClickCloseButton -> postSideEffect(ContentSideEffect.NavigateToBackStack)
            is ContentIntent.ClickSaveButton -> postSideEffect(ContentSideEffect.NavigateToBackStack)
            is ContentIntent.ClickDeleteButton -> postSideEffect(ContentSideEffect.NavigateToBackStack)
        }
    }

}
