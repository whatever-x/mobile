package com.whatever.caramel.feature.memo.mvi

import com.whatever.caramel.core.viewmodel.UiState
import com.whatever.caramel.feature.memo.model.MemoUiModel
import com.whatever.caramel.feature.memo.model.TagUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MemoState(
    val isMemoLoading: Boolean = true,
    val isTagLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val memos: ImmutableList<MemoUiModel> = persistentListOf(),
    val tags: ImmutableList<TagUiModel> = persistentListOf(),
    val selectedTag: TagUiModel? = null,
    val selectedChipIndex: Int = 0,
    val cursor: String? = null,
) : UiState {
    val isEmpty: Boolean
        get() = !isMemoLoading && memos.isEmpty()
}
