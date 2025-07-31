package com.whatever.caramel.core.domain_change.params.content

import com.whatever.caramel.core.domain.params.schedule.ScheduleDateTime
import com.whatever.caramel.core.domain.vo.content.ContentAssignee

/**
 * UseCase에서 많은 파라미터를 가질때 필요한 파라미터들
 * ContentInfo(VO)와 비슷하지만 ID만 전달한다던가 nullable하다던가 등 다른 부분들이 존재
 * */
data class MemoEditParameter(
    val title: String?,
    val description: String?,
    val isCompleted: Boolean?,
    val tagIds: List<Long>?,
    val scheduleDateTime: ScheduleDateTime?,
    val contentAssignee: ContentAssignee,
)