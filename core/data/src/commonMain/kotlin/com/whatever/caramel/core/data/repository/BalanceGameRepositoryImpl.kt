package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toBalanceGameResult
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.repository.BalanceGameRepository
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameResult
import com.whatever.caramel.core.remote.datasource.RemoteBalanceGameDataSource

class BalanceGameRepositoryImpl(
    private val remoteBalanceGameDataSource: RemoteBalanceGameDataSource
): BalanceGameRepository {

    override suspend fun getTodayBalanceGame(): BalanceGameResult {
        return safeCall {
            remoteBalanceGameDataSource.fetchTodayBalanceGame().toBalanceGameResult()
        }
    }

    override suspend fun submitOption(optionId: Long): BalanceGameResult {
        return safeCall {
            remoteBalanceGameDataSource.postChoiceOption(optionId = optionId).toBalanceGameResult()
        }
    }

}