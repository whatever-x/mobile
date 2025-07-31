package com.whatever.caramel.core.domain_change.params.schedule

/**
 *
 * */
data class ScheduleDateTime(
    val startDateTime: String,
    val startTimezone: String,
    val endDateTime: String?,
    val endTimezone: String?,
)