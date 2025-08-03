package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentData
import kotlinx.datetime.LocalDate

data class Memo(
    val id: Long,
    val tagList: List<Tag>,
    val contentData: ContentData,
    val createdAt: LocalDate,
)
