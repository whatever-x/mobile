package com.whatever.caramel.core.domain.vo.content.schedule

import kotlinx.datetime.LocalDateTime

data class DateTimeInfo(
    val startDateTime: LocalDateTime,
    val startTimezone: String,
    val endDateTime: LocalDateTime,
    val endTimezone: String,
)
