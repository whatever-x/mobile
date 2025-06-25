package com.whatever.caramel.feature.memo.mvi

import androidx.compose.runtime.Immutable
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
    val tags: ImmutableList<TagUiModel> = persistentListOf(),
    val selectedTag: TagUiModel? = null,
    val cursor : String? = null
) : UiState {
    val isEmpty : Boolean
        get() = !isMemoLoading && memos.isEmpty()
}

@Immutable
data class TagUiModel(
    val id : Long? = null,
    val label : String = ""
) {
    companion object {
        fun toUiModel(tag : Tag) = TagUiModel(
            id = tag.id,
            label = tag.label
        )
    }
}