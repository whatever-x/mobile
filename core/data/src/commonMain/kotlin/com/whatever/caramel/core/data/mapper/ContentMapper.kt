package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.vo.memo.MemoMetadata
import com.whatever.caramel.core.remote.dto.content.response.CreateMemoResponse

internal fun CreateMemoResponse.toMemoMetaData(): MemoMetadata {
    return MemoMetadata(
        contentId = contentId,
        contentType = contentType
    )
} 