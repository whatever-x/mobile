package com.whatever.caramel.feature.memo.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.feature.memo.model.MemoUiModel
import com.whatever.caramel.feature.memo.model.TagUiModel
import com.whatever.caramel.feature.memo.model.toUiModel
import com.whatever.caramel.feature.memo.mvi.MemoState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

internal class MemoScreenPreviewProvider : PreviewParameterProvider<MemoState> {
    override val values: Sequence<MemoState>
        get() =
            sequenceOf(
                MemoState(
                    isMemoLoading = true,
                    memos = persistentListOf(),
                    tags = persistentListOf(),
                    selectedTag = null,
                ),
                MemoState(
                    isMemoLoading = false,
                    memos = createTempMemoList(size = 100, emptyTitle = true),
                    tags = createTempTagUiList(size = 100),
                    selectedTag = TagUiModel(1, "label1"),
                ),
                MemoState(
                    isMemoLoading = false,
                    memos = createTempMemoList(size = 5, emptyTitle = false),
                    tags = createTempTagUiList(size = 5),
                    selectedTag = TagUiModel(1, "label1"),
                ),
            )

    private fun createTempMemoList(
        size: Int,
        emptyTitle: Boolean = false,
    ): ImmutableList<MemoUiModel> {
        val list = mutableListOf<Memo>()
        for (index in 0 until size) {
            val role =
                when {
                    index % 2 == 0 -> ContentAssignee.US
                    index % 3 == 0 -> ContentAssignee.PARTNER
                    else -> ContentAssignee.ME
                }
            list.add(
                Memo(
                    id = index.toLong(),
                    title = if (emptyTitle) "" else "title$index",
                    description = "description$index",
                    isCompleted = false,
                    tagList = createTempTagList(index),
                    createdAt = DateUtil.today(),
                    contentAssignee = role,
                ),
            )
        }
        return list.map { it.toUiModel() }.toImmutableList()
    }

    private fun createTempTagList(size: Int): ImmutableList<Tag> {
        val list = mutableListOf<Tag>()
        for (index in 0 until size) {
            list.add(
                Tag(
                    id = index.toLong(),
                    label = "label$index",
                ),
            )
        }
        return list.toImmutableList()
    }

    private fun createTempTagUiList(size: Int): ImmutableList<TagUiModel> {
        val list = mutableListOf<Tag>()
        for (index in 0 until size) {
            list.add(
                Tag(
                    id = index.toLong(),
                    label = "label$index",
                ),
            )
        }
        return list.map { TagUiModel.toUiModel(it) }.toImmutableList()
    }
}
