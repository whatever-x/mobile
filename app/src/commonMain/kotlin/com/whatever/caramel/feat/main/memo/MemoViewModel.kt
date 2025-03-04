package com.whatever.caramel.feat.main.memo

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.presentation.BaseViewModel
import com.whatever.caramel.feat.main.memo.mvi.MemoIntent
import com.whatever.caramel.feat.main.memo.mvi.MemoSideEffect
import com.whatever.caramel.feat.main.memo.mvi.MemoState

class MemoViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MemoState, MemoSideEffect, MemoIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): MemoState {
        return MemoState()
    }

    override suspend fun handleIntent(intent: MemoIntent) {
        when (intent) {
            is MemoIntent.ClickMemo -> postSideEffect(MemoSideEffect.NavigateToTodoDetail)
        }
    }

}