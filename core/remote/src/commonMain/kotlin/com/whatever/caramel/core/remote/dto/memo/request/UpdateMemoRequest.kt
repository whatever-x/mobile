package com.whatever.caramel.core.remote.dto.memo.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateMemoRequest(
    val title: String?,
    val description: String?,
    val isCompleted: Boolean?,
    val tagList: List<Long>?,
    val dateTimeInfo: DateTimeInfoRequest?
)

@Serializable
data class DateTimeInfoRequest(
    val startDateTime: String,
    val startTimezone: String,
    val endDateTime: String?,
    val endTimezone: String?
)