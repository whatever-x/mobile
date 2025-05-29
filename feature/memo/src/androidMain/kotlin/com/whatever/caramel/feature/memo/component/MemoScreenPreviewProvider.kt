package com.whatever.caramel.feature.memo.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.feature.memo.mvi.MemoState

internal class MemoScreenPreviewProvider : PreviewParameterProvider<MemoState> {
    override val values: Sequence<MemoState>
        get() = sequenceOf(
            MemoState(
                isLoading = true,
                memos = emptyList(),
                tags = emptyList(),
                selectedTag = null
            ),
            MemoState(
                isLoading = false,
                memos = createTempMemoList(size = 100, emptyTitle = true),
                tags = createTempTagList(size = 100),
                selectedTag = Tag(1, "label1")
            ),
            MemoState(
                isLoading = false,
                memos = createTempMemoList(size = 5, emptyTitle = false),
                tags = createTempTagList(size = 5),
                selectedTag = Tag(1, "label1")
            ),
        )

    private fun createTempMemoList(size: Int, emptyTitle: Boolean = false): List<Memo> {
        val list = mutableListOf<Memo>()
        for (index in 0 until size) {
            list.add(
                Memo(
                    id = index.toLong(),
                    title = if (emptyTitle) "" else "title$index",
                    description = "description$index",
                    isCompleted = false,
                    tagList = createTempTagList(index),
                    createdAt = DateUtil.today()
                )
            )
        }
        return list.toList()
    }

    private fun createTempTagList(size: Int): List<Tag> {
        val list = mutableListOf<Tag>()
        for (index in 0 until size) {
            list.add(
                Tag(
                    id = index.toLong(),
                    label = "label$index"
                )
            )
        }
        return list.toList()
    }
}