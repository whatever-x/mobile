package com.whatever.caramel.feat.main.presentation.memo.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface MemoSideEffect : UiSideEffect {

    data object NavigateToTodoDetail : MemoSideEffect

}