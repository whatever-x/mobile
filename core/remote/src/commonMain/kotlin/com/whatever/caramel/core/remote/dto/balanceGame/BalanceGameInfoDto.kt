package com.whatever.caramel.core.remote.dto.balanceGame

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BalanceGameInfoDto(
    @SerialName("id") val id: Long,
    @SerialName("date") val date: String,
    @SerialName("question") val question: String,
    @SerialName("options") val options: List<OptionInfoDto>,
)
