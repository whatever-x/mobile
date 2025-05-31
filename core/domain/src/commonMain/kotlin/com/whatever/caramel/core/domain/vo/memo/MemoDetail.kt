package com.whatever.caramel.core.domain.vo.memo

import com.whatever.caramel.core.domain.entity.Tag

data class MemoDetail(
    val id: Long,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val tagList: List<Tag>,
    val createdAt: String
)
