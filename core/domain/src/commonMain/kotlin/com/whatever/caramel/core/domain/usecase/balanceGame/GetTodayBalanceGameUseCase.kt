package com.whatever.caramel.core.domain.usecase.balanceGame

import com.whatever.caramel.core.domain.repository.BalanceGameRepository
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameResult

class GetTodayBalanceGameUseCase(
    private val balanceGameRepository: BalanceGameRepository,
) {
    suspend operator fun invoke(): BalanceGameResult = balanceGameRepository.getTodayBalanceGame()
}
