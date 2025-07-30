package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.vo.content.ContentMetaData
import kotlinx.datetime.LocalDateTime

data class Schedule(
    val id: Long,
    val tagList: List<Tag>,
    val metaData: ContentMetaData,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
) {
    val url: String?
        get() {
            val allContent = "${metaData.title} ${metaData.description}"
            return URL_PATTERN.findAll(allContent).map { it.value }.firstOrNull()
        }

    companion object Companion {
        val URL_PATTERN =
            Regex(
                "(https?://|www\\.)[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)",
            )
    }
}
