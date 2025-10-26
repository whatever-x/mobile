package com.whatever.caramel.core.domain.usecase.balanceGame

import com.whatever.caramel.core.domain.repository.BalanceGameRepository
import com.whatever.caramel.core.domain.usecase.app.IncrementActivityParticipationCountUseCase
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameResult

class SubmitBalanceGameChoiceUseCase(
    private val balanceGameRepository: BalanceGameRepository,
    private val incrementActivityParticipationCountUseCase: IncrementActivityParticipationCountUseCase,
) {
    suspend operator fun invoke(
        gameId: Long,
        optionId: Long,
    ): BalanceGameResult {
        val result =
            balanceGameRepository.submitOption(
                gameId = gameId,
                optionId = optionId,
            )
        if (result.partnerChoice != null) incrementActivityParticipationCountUseCase()
        return result
    }
}
