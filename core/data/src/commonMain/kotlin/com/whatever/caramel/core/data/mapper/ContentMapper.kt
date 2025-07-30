package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.model.content.ContentMetadata
import com.whatever.caramel.core.domain.model.content.ContentWithCursor
import com.whatever.caramel.core.remote.dto.memo.response.CreateMemoResponse
import com.whatever.caramel.core.remote.dto.memo.response.CursoredContentResponse
import com.whatever.caramel.core.remote.dto.memo.response.MemoResponse
import kotlinx.datetime.LocalDate

internal fun CreateMemoResponse.toMemoMetaData(): ContentMetadata =
    ContentMetadata(
        contentId = contentId,
        contentType = contentType,
    )

internal fun MemoResponse.toMemo(): Memo =
    Memo(
        id = this.id,
        title = this.title ?: "",
        description = this.description ?: "",
        isCompleted = this.isCompleted,
        tagList = this.tagList.toTags(),
        createdAt = LocalDate.parse(this.createdAt),
        contentAssignee = ContentAssignee.valueOf(this.contentAssignee.name),
    )

internal fun CursoredContentResponse.toMemosWithCursor(): ContentWithCursor =
    ContentWithCursor(
        nextCursor = this.cursor.next,
        memos = this.list.map { it.toMemo() },
    )
