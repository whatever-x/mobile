package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.balanceGame.request.ChooseBalanceGameRequest
import com.whatever.caramel.core.remote.dto.balanceGame.response.BalanceGameHistoryResponse
import com.whatever.caramel.core.remote.dto.balanceGame.response.BalanceGameResponse

interface RemoteBalanceGameDataSource {
    suspend fun fetchBalanceGameOfToday(): BalanceGameResponse

    suspend fun sendChooseOption(
        gameId: Long,
        request: ChooseBalanceGameRequest,
    ): BalanceGameResponse

    suspend fun fetchBalanceGameHistory(
        size: Int?,
        cursor: String?,
    ): BalanceGameHistoryResponse
}
