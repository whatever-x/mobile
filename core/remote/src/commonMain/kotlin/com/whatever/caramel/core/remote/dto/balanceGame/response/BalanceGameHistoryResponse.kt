package com.whatever.caramel.core.remote.dto.balanceGame.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BalanceGameHistoryResponse(
    @SerialName("list") val list: List<BalanceGameResponse>,
    @SerialName("cursor") val cursor: BalanceGameHistoryCursor,
)

@Serializable
data class BalanceGameHistoryCursor(
    @SerialName("next") val next: String?,
)
