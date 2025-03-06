package com.whatever.caramel.core.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SamplePostMethodResponseDto(
    @SerialName("result") val result: String,
    @SerialName("detail") val detail: String,
)
