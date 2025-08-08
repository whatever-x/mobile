package com.whatever.caramel.core.domain.usecase.balanceGame

import com.whatever.caramel.core.domain.repository.BalanceGameRepository
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameResult

class SubmitBalanceGameChoiceUseCase(
    private val balanceGameRepository: BalanceGameRepository,
) {
    suspend operator fun invoke(
        gameId: Long,
        optionId: Long,
    ): BalanceGameResult =
        balanceGameRepository.submitOption(
            gameId = gameId,
            optionId = optionId,
        )
}
