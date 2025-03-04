package com.whatever.caramel.feat.sample.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SamplePostMethodRequestDto(
    @SerialName("name") val name: String
)