package com.whatever.caramel.feature.memo.mvi

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MemoState(
    val isTagLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val memoContent: MemoContentState = MemoContentState.Loading,
    val tagList: ImmutableList<Tag> = persistentListOf(Tag(id = 0L, label = "")),
    val selectedTag: Tag? = tagList[0],
    val cursor: String? = null,
) : UiState

sealed interface MemoContentState {

    data object Loading : MemoContentState

    data object Empty : MemoContentState

    data class Content(
        val memoList: ImmutableList<Memo>
    ) : MemoContentState

}