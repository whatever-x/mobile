package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.policy.ContentPolicy.URL_PATTERN
import com.whatever.caramel.core.domain.vo.content.ContentInfo
import kotlinx.datetime.LocalDateTime


data class Schedule(
    val id : Long,
    val parentScheduleId: Long?,
    val contentInfo : ContentInfo,
    val startDate : LocalDateTime,
    val endDate : LocalDateTime,
    val startTimeZone : String,
    val endTimeZone : String,
) {
    val url: String?
        get() {
            val allContent = "${contentInfo.title} ${contentInfo.description}"
            return URL_PATTERN.findAll(allContent).map { it.value }.firstOrNull()
        }
}