package com.whatever.caramel.feature.memo.mvi

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.viewmodel.UiState

data class MemoState(
    val isLoading : Boolean = false,
    val memos: List<Memo> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val selectedTag: Tag? = null
) : UiState