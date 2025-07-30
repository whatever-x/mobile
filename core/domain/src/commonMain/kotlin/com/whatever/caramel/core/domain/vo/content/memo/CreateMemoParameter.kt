package com.whatever.caramel.core.domain.vo.content.memo

import com.whatever.caramel.core.domain.vo.content.ContentAssignee

data class CreateMemoParameter(
    val title: String?,
    val description: String?,
    val isCompleted: Boolean,
    val tags: List<Long>?,
    val contentAssignee: ContentAssignee,
)