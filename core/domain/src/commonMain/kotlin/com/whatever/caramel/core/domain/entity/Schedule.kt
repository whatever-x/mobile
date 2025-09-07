package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.policy.ContentPolicy
import com.whatever.caramel.core.domain.vo.content.ContentData
import com.whatever.caramel.core.domain.vo.content.schedule.DateTimeInfo
import kotlinx.datetime.LocalDate
import kotlin.collections.get

data class Schedule(
    val id: Long,
    val tagList: List<Tag>,
    val contentData: ContentData,
    val dateTimeInfo: DateTimeInfo,
) {
    val url: String?
        get() {
            val allContent = "${contentData.title} ${contentData.description}"
            return ContentPolicy.URL_PATTERN
                .findAll(allContent)
                .map { it.value }
                .firstOrNull()
        }
}