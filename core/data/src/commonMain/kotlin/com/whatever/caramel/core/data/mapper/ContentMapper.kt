package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.vo.memo.MemoDetail
import com.whatever.caramel.core.domain.vo.memo.MemoMetadata
import com.whatever.caramel.core.remote.dto.memo.response.CreateMemoResponse
import com.whatever.caramel.core.remote.dto.memo.response.GetMemoResponse

internal fun CreateMemoResponse.toMemoMetaData(): MemoMetadata {
    return MemoMetadata(
        contentId = contentId,
        contentType = contentType
    )
}

internal fun GetMemoResponse.toMemoDetail(): MemoDetail {
    return MemoDetail(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        tagList = tagList.map { it.toTag() },
        createdAt = createdAt
    )
}
