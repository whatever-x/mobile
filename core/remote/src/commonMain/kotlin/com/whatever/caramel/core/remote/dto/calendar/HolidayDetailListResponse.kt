package com.whatever.caramel.core.remote.dto.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HolidayDetailListResponse(
    @SerialName("holidayList") val holidayList: List<HolidayDetailDto>,
)

@Serializable
data class HolidayDetailDto(
    @SerialName("id") val id: Long,
    @SerialName("type") val type: String,
    @SerialName("date") val date: String,
    @SerialName("name") val name: String,
    @SerialName("isHoliday") val isHoliday: Boolean,
)
