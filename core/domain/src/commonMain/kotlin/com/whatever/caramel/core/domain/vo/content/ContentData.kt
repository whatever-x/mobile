package com.whatever.caramel.core.domain.vo.content

data class ContentData(
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val contentAssignee: ContentAssignee,
)
