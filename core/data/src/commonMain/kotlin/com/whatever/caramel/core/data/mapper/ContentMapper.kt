package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentMetaData
import com.whatever.caramel.core.domain.vo.content.memo.MemoWithCursor
import com.whatever.caramel.core.remote.dto.memo.response.CreateMemoResponse
import com.whatever.caramel.core.remote.dto.memo.response.CursoredContentResponse
import com.whatever.caramel.core.remote.dto.memo.response.MemoResponse
import kotlinx.datetime.LocalDate

// 컨텐츠 생성시 반환값에 대한 매퍼. 사용 하지 않는 매퍼
//internal fun CreateMemoResponse.toMemoMetaData(): MemoMetadata =
//    MemoMetadata(
//        contentId = contentId,
//        contentType = contentType,
//    )

internal fun MemoResponse.toMemo(): Memo =
    Memo(
        id = this.id,
        tagList = this.tagList.toTags(),
        createdAt = LocalDate.parse(this.createdAt),
        metaData = ContentMetaData(
            title = this.title ?: "",
            description = this.description ?: "",
            contentAssignee = ContentAssignee.valueOf(value = this.contentAssignee.name),
            isCompleted = this.isCompleted,
        ),
    )

internal fun CursoredContentResponse.toMemosWithCursor(): MemoWithCursor =
    MemoWithCursor(
        nextCursor = this.cursor.next,
        memos = this.list.map { it.toMemo() },
    )
