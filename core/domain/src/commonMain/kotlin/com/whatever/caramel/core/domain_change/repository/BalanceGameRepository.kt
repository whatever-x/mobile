package com.whatever.caramel.core.domain_change.repository

import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameResult

interface BalanceGameRepository {
    suspend fun getTodayBalanceGame(): BalanceGameResult

    suspend fun submitOption(
        gameId: Long,
        optionId: Long,
    ): BalanceGameResult
}
