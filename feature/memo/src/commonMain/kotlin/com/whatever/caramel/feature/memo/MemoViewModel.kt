package com.whatever.caramel.feature.memo

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.memo.mvi.MemoIntent
import com.whatever.caramel.feature.memo.mvi.MemoSideEffect
import com.whatever.caramel.feature.memo.mvi.MemoState

class MemoViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MemoState, MemoSideEffect, MemoIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): MemoState {
        return MemoState()
    }

    override suspend fun handleIntent(intent: MemoIntent) {
        when (intent) {
            is MemoIntent.ClickMemo -> postSideEffect(MemoSideEffect.NavigateToTodoDetail(intent.memoId))
            is MemoIntent.ClickTagChip -> TODO()
            MemoIntent.PullToRefresh -> TODO()
        }
    }

}