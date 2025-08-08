package com.whatever.caramel.core.domain.vo.balanceGame

import com.whatever.caramel.core.domain.entity.BalanceGame

data class BalanceGameResult(
    val gameInfo: BalanceGame,
    val myChoice: BalanceGame.Option?,
    val partnerChoice: BalanceGame.Option?,
)
