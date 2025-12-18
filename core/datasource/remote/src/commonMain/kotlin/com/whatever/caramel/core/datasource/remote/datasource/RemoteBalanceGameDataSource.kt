package com.whatever.caramel.core.datasource.remote.datasource

import com.whatever.caramel.core.datasource.remote.dto.balanceGame.request.ChooseBalanceGameRequest
import com.whatever.caramel.core.datasource.remote.dto.balanceGame.response.BalanceGameResponse

interface RemoteBalanceGameDataSource {
    suspend fun fetchBalanceGameOfToday(): BalanceGameResponse

    suspend fun sendChooseOption(
        gameId: Long,
        request: ChooseBalanceGameRequest,
    ): BalanceGameResponse
}
