package com.whatever.caramel.core.domain.params.content

import com.whatever.caramel.core.domain.params.schedule.DateTimeInfo
import com.whatever.caramel.core.domain.vo.content.ContentAssignee

data class MemoEditParameter(
    val title: String?,
    val description: String?,
    val isCompleted: Boolean?,
    val tagIds: List<Long>?,
    val dateTimeInfo: DateTimeInfo?,
    val contentAssignee: ContentAssignee,
)