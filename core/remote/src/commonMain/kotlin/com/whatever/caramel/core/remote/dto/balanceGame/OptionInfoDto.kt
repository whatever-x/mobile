package com.whatever.caramel.core.remote.dto.balanceGame

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OptionInfoDto(
    @SerialName("id") val optionId: Long,
    @SerialName("text") val text: String,
)