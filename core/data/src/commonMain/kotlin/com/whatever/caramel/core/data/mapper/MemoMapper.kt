package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentData
import com.whatever.caramel.core.domain.vo.content.memo.MemoWithCursor
import com.whatever.caramel.core.remote.dto.memo.response.CursoredContentResponse
import com.whatever.caramel.core.remote.dto.memo.response.MemoResponse
import kotlinx.datetime.LocalDate

internal fun MemoResponse.toMemo(): Memo =
    Memo(
        id = this.id,
        contentData =
            ContentData(
                title = this.title ?: "",
                description = this.description ?: "",
                isCompleted = this.isCompleted,
                contentAssignee = ContentAssignee.valueOf(this.contentAssignee.name),
            ),
        tagList = this.tagList.toTagList(),
        createdAt = LocalDate.parse(this.createdAt),
    )

internal fun CursoredContentResponse.toMemosWithCursor(): MemoWithCursor =
    MemoWithCursor(
        nextCursor = this.cursor.next,
        memos = this.list.map { it.toMemo() },
    )
