package com.whatever.caramel.core.remote.dto.balanceGame.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChooseBalanceGameRequest(
    @SerialName("optionId") val optionId: Long,
)
