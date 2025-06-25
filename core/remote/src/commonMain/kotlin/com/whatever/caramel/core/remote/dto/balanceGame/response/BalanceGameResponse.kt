package com.whatever.caramel.core.remote.dto.balanceGame.response

import com.whatever.caramel.core.remote.dto.balanceGame.BalanceGameInfoDto
import com.whatever.caramel.core.remote.dto.balanceGame.OptionInfoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BalanceGameResponse(
    @SerialName("gameInfo") val gameInfo: BalanceGameInfoDto,
    @SerialName("myChoice") val myChoice: OptionInfoDto?,
    @SerialName("partnerChoice") val partnerChoice: OptionInfoDto?,
)
