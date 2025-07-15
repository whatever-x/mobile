package com.whatever.caramel.feature.memo.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface MemoIntent : UiIntent {
    data object Initialize : MemoIntent

    data class ClickMemo(
        val memoId: Long,
    ) : MemoIntent

    data object PullToRefresh : MemoIntent

    data class ClickTagChip(
        val tag: TagUiModel,
        val index: Int,
    ) : MemoIntent

    data object ReachedEndOfList : MemoIntent
}
