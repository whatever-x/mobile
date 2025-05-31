package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.vo.memo.MemoMetadata
import com.whatever.caramel.core.domain.vo.memo.MemoWithCursor
import com.whatever.caramel.core.remote.dto.content.response.ContentResponse
import com.whatever.caramel.core.remote.dto.content.response.CreateMemoResponse
import com.whatever.caramel.core.remote.dto.content.response.CursoredContentResponse
import kotlinx.datetime.LocalDate

internal fun CreateMemoResponse.toMemoMetaData(): MemoMetadata {
    return MemoMetadata(
        contentId = contentId,
        contentType = contentType
    )
}

internal fun ContentResponse.toMemo(): Memo {
    return Memo(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        tagList = this.tagList.toTags(),
        createdAt = LocalDate.parse(this.createdAt)
    )
}

internal fun CursoredContentResponse.toMemosWithCursor(): MemoWithCursor {
    return MemoWithCursor(
        nextCursor = this.cursor.next,
        memos = this.list.map { it.toMemo() }
    )
}