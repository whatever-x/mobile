package com.whatever.caramel.core.domain_change.vo.content

import com.whatever.caramel.core.domain.entity.Content
import kotlinx.datetime.LocalDate

data class ContentInfo(
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val createdAt: LocalDate,
    val contentAssignee: ContentAssignee,
){
    // 고유의 값들
}