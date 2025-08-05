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

internal fun MemoParameter.toCreateMemoRequest(): CreateMemoRequest =
    CreateMemoRequest(
        title = title,
        description = description,
        isCompleted = isCompleted,
        tags = tags?.map { tagId -> TagRequest(id = tagId) },
        contentAssignee = ContentAssigneeDto.valueOf(value = contentAssignee.name),
    )

internal fun MemoEditParameter.toUpdateMemoRequest(): UpdateMemoRequest =
    UpdateMemoRequest(
        title = title,
        description = description,
        isCompleted = isCompleted,
        tagList = tagIds?.map { TagRequest(it) },
        dateTimeInfo =
            dateTimeInfo?.run {
                DateTimeInfoRequest(
                    startDateTime = startDateTime.toString(),
                    startTimezone = startTimezone,
                    endDateTime = endDateTime.toString(),
                    endTimezone = endTimezone,
                )
            },
        contentAssignee = ContentAssigneeDto.valueOf(value = contentAssignee.name),
    )
