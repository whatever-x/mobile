package com.whatever.caramel.core.domain.params.schedule

data class DateTimeInfo(
    val startDateTime: String,
    val startTimezone: String,
    val endDateTime: String?,
    val endTimezone: String?,
)