package com.whatever.caramel.core.remote.dto.content.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateMemoRequest(
    val title: String?,
    val description: String?,
    val isCompleted: Boolean?,
    val tagList: List<Int>?,
    val dateTimeInfo: DateTimeInfoRequest?
)

@Serializable
data class DateTimeInfoRequest(
    val startDateTime: String,
    val startTimezone: String,
    val endDateTime: String?,
    val endTimezone: String?
)