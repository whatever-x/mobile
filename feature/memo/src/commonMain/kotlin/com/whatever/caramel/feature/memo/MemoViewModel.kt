package com.whatever.caramel.feature.memo

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.content.GetMemosUseCase
import com.whatever.caramel.core.domain.usecase.tag.GetTagUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.memo.mvi.MemoIntent
import com.whatever.caramel.feature.memo.mvi.MemoSideEffect
import com.whatever.caramel.feature.memo.mvi.MemoState
import com.whatever.caramel.feature.memo.mvi.TagUiModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

class MemoViewModel(
    private val getMemosUseCase: GetMemosUseCase,
    private val getTagUseCase: GetTagUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MemoState, MemoSideEffect, MemoIntent>(savedStateHandle) {

    init {
        getMemos()
        getTags()
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): MemoState {
        return MemoState()
    }

    override suspend fun handleIntent(intent: MemoIntent) {
        when (intent) {
            is MemoIntent.ClickMemo -> clickMemo(intent)
            is MemoIntent.ClickTagChip -> clickTagChip(intent)
            MemoIntent.PullToRefresh -> refreshMemos()
            MemoIntent.ReachedEndOfList -> loadPagingData()
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        reduce {
            copy(
                isMemoLoading = false,
                isRefreshing = false,
                isTagLoading = false
            )
        }
    }

    private fun getTags() {
        reduce { copy(isTagLoading = true) }
        launch {
            val tags = getTagUseCase().map { TagUiModel.toUiModel(it) }
            val combinedTags = listOf(TagUiModel()) + tags
            reduce {
                copy(
                    isTagLoading = false,
                    tags = combinedTags.toImmutableList(),
                    selectedTag = combinedTags.first()
                )
            }
        }
    }

    private fun clickMemo(intent: MemoIntent.ClickMemo) {
        postSideEffect(MemoSideEffect.NavigateToTodoDetail(intent.memoId))
    }

    private fun loadPagingData(){
        if(currentState.cursor == null && currentState.memos.isNotEmpty()) return
        getMemos()
    }

    private fun clickTagChip(intent: MemoIntent.ClickTagChip) {
        if (currentState.selectedTag == intent.tag) return

        reduce {
            copy(
                isMemoLoading = true,
                selectedTag = intent.tag,
                cursor = null,
                memos = persistentListOf()
            )
        }
        getMemos()
    }

    private fun refreshMemos() {
        reduce {
            copy(
                isRefreshing = true,
                isMemoLoading = true,
                cursor = null,
                memos = persistentListOf()
            )
        }
        getMemos()
    }

    private fun getMemos() {
        launch {
            val newMemos = getMemosUseCase(
                size = 10,
                cursor = currentState.cursor,
                tagId = currentState.selectedTag?.id
            )
            val updatedMemos = if (newMemos.memos.isEmpty()) {
                currentState.memos
            } else {
                currentState.memos + newMemos.memos
            }
            reduce {
                copy(
                    isMemoLoading = false,
                    isRefreshing = false,
                    cursor = newMemos.nextCursor,
                    memos = updatedMemos.toImmutableList()
                )
            }
        }
    }
}