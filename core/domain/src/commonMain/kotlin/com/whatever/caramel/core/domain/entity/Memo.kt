package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.vo.content.ContentMetaData
import kotlinx.datetime.LocalDate

data class Memo(
    val id: Long,
    val tagList: List<Tag>,
    val metaData: ContentMetaData,
    val createdAt: LocalDate,
)
