package com.whatever.caramel.core.domain.vo.content.schedule

data class DateTimeInfo(
    val startDateTime: String,
    val startTimezone: String,
    val endDateTime: String?,
    val endTimezone: String?,
)