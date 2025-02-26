package com.whatever.caramel.feat.main.presentation.memo

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.presentation.BaseViewModel
import com.whatever.caramel.feat.main.presentation.memo.mvi.MemoIntent
import com.whatever.caramel.feat.main.presentation.memo.mvi.MemoSideEffect
import com.whatever.caramel.feat.main.presentation.memo.mvi.MemoState

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