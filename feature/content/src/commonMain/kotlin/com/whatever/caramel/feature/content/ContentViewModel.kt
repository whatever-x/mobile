package com.whatever.caramel.feature.content

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.whatever.caramel.core.domain.usecase.tag.GetTagUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.content.mvi.ContentIntent
import com.whatever.caramel.feature.content.mvi.ContentSideEffect
import com.whatever.caramel.feature.content.mvi.ContentState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import com.whatever.caramel.feature.content.navigation.ContentRoute

class ContentViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTagUseCase: GetTagUseCase
) : BaseViewModel<ContentState, ContentSideEffect, ContentIntent>(savedStateHandle) {

    init {
        launch {
            val tags = getTagUseCase()
            reduce {
                copy(tags = tags.toImmutableList())
            }
        }
    }

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
            is ContentIntent.InputTitle -> {
                inputTitle(intent)
            }

            is ContentIntent.InputContent -> {
                inputContent(intent)
            }

            is ContentIntent.RecognizeLink -> {

            }

            is ContentIntent.ClickTag -> {
                toggleTagSelection(intent)
            }

            is ContentIntent.SelectCreateMode -> selectCreateMode(intent)
        }
    }

    private fun inputTitle(intent: ContentIntent.InputTitle) {
        reduce {
            copy(
                title = intent.text
            )
        }
    }

    private fun inputContent(intent: ContentIntent.InputContent) {
        reduce {
            copy(
                content = intent.text
            )
        }
    }

    private fun toggleTagSelection(intent: ContentIntent.ClickTag) {
        reduce {
            copy(
                selectedTags = if (selectedTags.contains(intent.tag)) {
                    selectedTags - intent.tag
                } else {
                    selectedTags + intent.tag
                }.toImmutableSet()
            )
        }
    }

    private fun selectCreateMode(intent: ContentIntent.SelectCreateMode) {
        reduce {
            copy(
                createMode = intent.createMode
            )
        }
    }

}
