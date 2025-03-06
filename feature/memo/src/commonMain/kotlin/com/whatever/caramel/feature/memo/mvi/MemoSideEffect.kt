package com.whatever.caramel.feature.memo.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface MemoSideEffect : UiSideEffect {

    data object NavigateToTodoDetail : MemoSideEffect

}