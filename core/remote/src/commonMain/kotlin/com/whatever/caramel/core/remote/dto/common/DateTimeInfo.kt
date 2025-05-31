package com.whatever.caramel.core.remote.dto.common

import kotlinx.serialization.Serializable

@Serializable
data class DateTimeInfo(
    val startDateTime: String,
    val startTimezone: String,
    val endDateTime: String,
    val endTimezone: String
) 