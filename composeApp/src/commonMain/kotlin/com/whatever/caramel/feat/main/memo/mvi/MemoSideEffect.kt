package com.whatever.caramel.feat.main.memo.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface MemoSideEffect : UiSideEffect {

    data object NavigateToTodoDetail : MemoSideEffect

}