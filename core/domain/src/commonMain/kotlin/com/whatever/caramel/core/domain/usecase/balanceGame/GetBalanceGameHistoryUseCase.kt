package com.whatever.caramel.core.domain.usecase.balanceGame

import com.whatever.caramel.core.domain.repository.BalanceGameRepository
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameHistory

class GetBalanceGameHistoryUseCase(
    private val balanceGameRepository: BalanceGameRepository,
) {
    suspend operator fun invoke(
        size: Int? = null,
        cursor: String? = null,
    ): BalanceGameHistory =
        balanceGameRepository.getBalanceGameHistory(
            size = size,
            cursor = cursor,
        )
}
