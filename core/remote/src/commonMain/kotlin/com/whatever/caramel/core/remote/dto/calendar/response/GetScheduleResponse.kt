package com.whatever.caramel.core.remote.dto.calendar.response

import com.whatever.caramel.core.remote.dto.calendar.ScheduleApiResult
import com.whatever.caramel.core.remote.dto.tag.TagDetailResponse
import kotlinx.serialization.Serializable

@Serializable
data class GetScheduleResponse(
    val scheduleDetail: ScheduleApiResult,
    val tags: List<TagDetailResponse>,
)
