package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.memo.MemoMetadata
import com.whatever.caramel.core.domain.vo.memo.MemoWithCursor
import com.whatever.caramel.core.remote.dto.memo.response.CreateMemoResponse
import com.whatever.caramel.core.remote.dto.memo.response.CursoredContentResponse
import com.whatever.caramel.core.remote.dto.memo.response.MemoResponse
import kotlinx.datetime.LocalDate

internal fun CreateMemoResponse.toMemoMetaData(): MemoMetadata =
    MemoMetadata(
        contentId = contentId,
        contentType = contentType,
    )

internal fun MemoResponse.toMemo(): Memo =
    Memo(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        tagList = this.tagList.toTags(),
        createdAt = LocalDate.parse(this.createdAt),
        contentAssignee = ContentAssignee.valueOf(this.contentAssignee.name),
    )

internal fun CursoredContentResponse.toMemosWithCursor(): MemoWithCursor =
    MemoWithCursor(
        nextCursor = this.cursor.next,
        memos = this.list.map { it.toMemo() },
    )
