package com.whatever.caramel.core.domain.vo.memo

import com.whatever.caramel.core.domain.vo.content.ContentRole

data class MemoParameter(
    val title: String?,
    val description: String?,
    val isCompleted: Boolean,
    val tags: List<Long>?,
    val contentRole: ContentRole,
)
