package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.policy.ContentPolicy.URL_PATTERN
import com.whatever.caramel.core.domain.vo.content.ContentInfo
import kotlinx.datetime.LocalDateTime


/**
 * 스케쥴은 Content와 동일하지만 일정관련 데이터가 추가됨
 * */
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