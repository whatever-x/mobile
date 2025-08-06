package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.params.content.memo.MemoEditParameter
import com.whatever.caramel.core.domain.params.content.memo.MemoParameter
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentData
import com.whatever.caramel.core.domain.vo.content.memo.MemoWithCursor
import com.whatever.caramel.core.remote.dto.memo.ContentAssigneeDto
import com.whatever.caramel.core.remote.dto.memo.request.CreateMemoRequest
import com.whatever.caramel.core.remote.dto.memo.request.DateTimeInfoRequest
import com.whatever.caramel.core.remote.dto.memo.request.UpdateMemoRequest
import com.whatever.caramel.core.remote.dto.memo.response.CursoredContentResponse
import com.whatever.caramel.core.remote.dto.memo.response.MemoResponse
import com.whatever.caramel.core.remote.dto.tag.TagRequest
import kotlinx.datetime.LocalDate

internal fun MemoResponse.toMemo(): Memo =
    Memo(
        id = this.id,
        contentData = ContentData(
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
