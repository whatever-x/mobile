package com.whatever.caramel.core.remote.dto.sample.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SamplePostMethodRequestDto(
    @SerialName("name") val name: String
)