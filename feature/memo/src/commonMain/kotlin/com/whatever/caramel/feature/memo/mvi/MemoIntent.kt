package com.whatever.caramel.feature.memo.mvi

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface MemoIntent : UiIntent {

    data class ClickMemo(
        val memoId: Long,
    ) : MemoIntent

    data object PullToRefresh : MemoIntent

    data class ClickTagChip(
        val tag: Tag,
    ) : MemoIntent

    data object ReachedEndOfList : MemoIntent

    data class ClickRecommendMemo(
        val title: String,
    ) : MemoIntent
}
