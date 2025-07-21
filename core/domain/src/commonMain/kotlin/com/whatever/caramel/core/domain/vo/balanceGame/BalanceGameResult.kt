package com.whatever.caramel.core.domain.vo.balanceGame

import com.whatever.caramel.core.domain.entity.BalanceGame
import com.whatever.caramel.core.domain.entity.BalanceGameOption

data class BalanceGameResult(
    val gameInfo: BalanceGame,
    val myChoice: BalanceGameOption?,
    val partnerChoice: BalanceGameOption?,
)
