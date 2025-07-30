package com.whatever.caramel.core.domain.model.content

import com.whatever.caramel.core.domain.entity.Content

data class ContentWithCursor(
    val nextCursor: String?,
    val memos: List<Content>,
)