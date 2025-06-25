package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameResult

interface BalanceGameRepository {
    suspend fun getTodayBalanceGame(): BalanceGameResult
    suspend fun submitOption(
        gameId: Long,
        optionId: Long
    ): BalanceGameResult
}