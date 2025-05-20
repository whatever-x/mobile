package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.vo.memo.MemoMetadata
import com.whatever.caramel.core.remote.dto.content.response.CreateContentResponse

internal fun CreateContentResponse.toContentMetaData(): MemoMetadata {
    return MemoMetadata(
        contentId = this.contentId,
        contentType = this.contentType
    )
} 