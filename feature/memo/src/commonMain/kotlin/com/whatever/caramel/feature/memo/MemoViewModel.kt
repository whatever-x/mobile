package com.whatever.caramel.feature.memo

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.usecase.content.GetMemosUseCase
import com.whatever.caramel.core.domain.usecase.tag.GetTagUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.memo.mvi.MemoIntent
import com.whatever.caramel.feature.memo.mvi.MemoSideEffect
import com.whatever.caramel.feature.memo.mvi.MemoState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

class MemoViewModel(
    private val getMemosUseCase: GetMemosUseCase,
    private val getTagUseCase: GetTagUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MemoState, MemoSideEffect, MemoIntent>(savedStateHandle) {

    init {
        getMemos(clear = false, pagingLoading = true)
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
            MemoIntent.ReachedEndOfList -> getMemos(clear = false)
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
            val tags = (listOf(Tag(id = 0, label = "전체")) + getTagUseCase())
            reduce { copy(isTagLoading = false, tags = tags.toImmutableList(), selectedTag = tags.first()) }
        }
    }

    private fun clickMemo(intent: MemoIntent.ClickMemo) {
        postSideEffect(MemoSideEffect.NavigateToTodoDetail(intent.memoId))
    }

    private fun clickTagChip(intent: MemoIntent.ClickTagChip) {
        reduce { copy(selectedTag = intent.tag) }
        getMemos(clear = true)
    }

    private fun refreshMemos() {
        reduce { copy(isRefreshing = true) }
        getMemos(clear = true)
    }

    private fun getMemos(clear: Boolean, pagingLoading : Boolean = false) {
        if (clear) reduce { copy(memos = persistentListOf(), cursor = null) }
        if(currentState.memos.isNotEmpty() && currentState.cursor == null) return
        reduce { copy(isMemoLoading = pagingLoading) }
        launch {
            val newMemos = getMemosUseCase(
                size = 5,
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