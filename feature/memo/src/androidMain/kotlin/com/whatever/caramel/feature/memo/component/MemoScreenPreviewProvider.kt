package com.whatever.caramel.feature.memo.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentData
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.feature.memo.mvi.MemoContentState
import com.whatever.caramel.feature.memo.mvi.MemoState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

internal class MemoScreenPreviewProvider : PreviewParameterProvider<MemoState> {
    override val values: Sequence<MemoState>
        get() {
            val tagList = createTempTagList(size = 100)
            val memoList = createTempMemoList(size = 10, emptyTitle = false)

            return sequenceOf(
                MemoState(
                    // 전체 로딩 상태
                    isTagLoading = true,
                    isRefreshing = false,
                    memoContent = MemoContentState.Loading,
                    tagList = persistentListOf(),
                    selectedTag = null,
                    cursor = null,
                ),
                MemoState(
                    // 메모 로딩 상태
                    isTagLoading = false,
                    isRefreshing = false,
                    memoContent = MemoContentState.Loading,
                    tagList = tagList,
                    selectedTag = tagList[0],
                    cursor = null,
                ),
                MemoState( // 태그 로딩 상태
                    isTagLoading = true,
                    isRefreshing = false,
                    memoContent = MemoContentState.Content(memoList = memoList),
                    tagList = persistentListOf(),
                    selectedTag = null,
                    cursor = null,
                ),
                MemoState(
                    // 전체 나온 상태
                    isTagLoading = false,
                    isRefreshing = false,
                    memoContent = MemoContentState.Content(memoList = memoList),
                    tagList = tagList,
                    selectedTag = tagList[0],
                    cursor = "1",
                ),
                MemoState(
                    // 메모가 없을 때
                    isTagLoading = false,
                    isRefreshing = false,
                    memoContent = MemoContentState.Empty,
                    tagList = tagList,
                    selectedTag = tagList[0],
                    cursor = "1",
                )
            )
        }

    private fun createTempMemoList(
        size: Int,
        emptyTitle: Boolean = false,
    ): ImmutableList<Memo> {
        val list = mutableListOf<Memo>()
        for (index in 0 until size) {
            val contentAssignee =
                when {
                    index % 2 == 0 -> ContentAssignee.US
                    index % 3 == 0 -> ContentAssignee.PARTNER
                    else -> ContentAssignee.ME
                }
            list.add(
                Memo(
                    id = index.toLong(),
                    contentData =
                        ContentData(
                            title = if (emptyTitle) "" else "title$index",
                            description = "description$index",
                            isCompleted = false,
                            contentAssignee = contentAssignee,
                        ),
                    tagList = createTempTagList(index),
                    createdAt = DateUtil.today(),
                ),
            )
        }
        return list.toImmutableList()
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

}
