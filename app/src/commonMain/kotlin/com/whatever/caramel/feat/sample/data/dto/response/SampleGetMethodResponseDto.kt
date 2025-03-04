package com.whatever.caramel.feat.sample.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SampleGetMethodResponseDto(
    @SerialName("name") val name: String,
    @SerialName("localDateTime") val localDateTime: String,
    @SerialName("detail") val detail: SampleDetailDto,
    @SerialName("detailArray") val detailArray: List<SampleDetailDto>,
)

@Serializable
data class SampleDetailDto(
    @SerialName("description") val description: String
)