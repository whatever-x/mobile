package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.balanceGame.request.ChooseBalanceGameRequest
import com.whatever.caramel.core.remote.dto.balanceGame.response.BalanceGameResponse

interface RemoteBalanceGameDataSource {
    suspend fun fetchTodayBalanceGame(): BalanceGameResponse

    suspend fun postChoiceOption(
        gameId: Long,
        request: ChooseBalanceGameRequest,
    ): BalanceGameResponse
}
