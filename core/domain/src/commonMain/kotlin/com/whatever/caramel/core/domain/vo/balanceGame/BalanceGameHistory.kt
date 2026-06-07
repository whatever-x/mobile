package com.whatever.caramel.core.domain.vo.balanceGame

data class BalanceGameHistory(
    val nextCursor: String?,
    val gameResults: List<BalanceGameResult>,
)
