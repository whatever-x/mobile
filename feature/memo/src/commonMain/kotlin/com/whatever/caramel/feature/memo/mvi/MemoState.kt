package com.whatever.caramel.feature.memo.mvi

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MemoState(
    val isMemoLoading : Boolean = true,
    val isTagLoading : Boolean = true,
    val isRefreshing : Boolean = false,
    val memos: ImmutableList<Memo> = persistentListOf(),
    val tags: ImmutableList<Tag> = persistentListOf(),
    val selectedTag: Tag? = null,
    val cursor : String? = null
) : UiState {
    val isEmpty : Boolean
        get() = !isMemoLoading && memos.isEmpty()
}