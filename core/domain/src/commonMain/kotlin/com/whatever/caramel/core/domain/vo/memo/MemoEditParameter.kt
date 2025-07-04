package com.whatever.caramel.core.domain.vo.memo

import com.whatever.caramel.core.domain.vo.common.DateTimeInfo

data class MemoEditParameter(
    val title: String?,
    val description: String?,
    val isCompleted: Boolean?,
    val tagIds: List<Long>?,
    val dateTimeInfo: DateTimeInfo?,
)
