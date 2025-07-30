package com.whatever.caramel.core.domain.vo.content

data class ContentMetaData(
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val contentAssignee: ContentAssignee,
)