package com.whatever.caramel.core.domain.vo.memo

data class MemoParameter(
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val tags: List<Long>?
) 