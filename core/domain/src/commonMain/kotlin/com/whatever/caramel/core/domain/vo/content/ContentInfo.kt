package com.whatever.caramel.core.domain.vo.content

import com.whatever.caramel.core.domain.entity.Content
import kotlinx.datetime.LocalDate

data class ContentInfo(
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val tagList: List<Content.Tag>,
    val createdAt: LocalDate,
    val contentAssignee: ContentAssignee,
)