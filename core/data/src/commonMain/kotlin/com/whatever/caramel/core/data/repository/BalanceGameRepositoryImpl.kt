package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toBalanceGameResult
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.repository.BalanceGameRepository
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameResult
import com.whatever.caramel.core.remote.datasource.RemoteBalanceGameDataSource
import com.whatever.caramel.core.remote.dto.balanceGame.request.ChooseBalanceGameRequest

class BalanceGameRepositoryImpl(
    private val remoteBalanceGameDataSource: RemoteBalanceGameDataSource
): BalanceGameRepository {

    override suspend fun getTodayBalanceGame(): BalanceGameResult {
        return safeCall {
            remoteBalanceGameDataSource.fetchTodayBalanceGame().toBalanceGameResult()
        }
    }

    override suspend fun submitOption(gameId: Long, optionId: Long): BalanceGameResult {
        return safeCall {
            remoteBalanceGameDataSource.postChoiceOption(
                gameId = gameId,
                request = ChooseBalanceGameRequest(
                    optionId = optionId
                )
            ).toBalanceGameResult()
        }
    }

}