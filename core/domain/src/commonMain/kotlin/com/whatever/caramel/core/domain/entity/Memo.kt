package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import kotlinx.datetime.LocalDate

data class Memo(
    val id: Long,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val tagList: List<Tag>,
    val createdAt: LocalDate,
    val role: ContentAssignee,
)
