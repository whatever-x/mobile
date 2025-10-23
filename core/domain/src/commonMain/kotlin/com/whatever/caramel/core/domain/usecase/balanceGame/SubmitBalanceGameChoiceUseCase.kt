package com.whatever.caramel.core.domain.usecase.balanceGame

import com.whatever.caramel.core.domain.repository.BalanceGameRepository
import com.whatever.caramel.core.domain.usecase.app.AddActivityParticipationCountUseCase
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameResult

class SubmitBalanceGameChoiceUseCase(
    private val balanceGameRepository: BalanceGameRepository,
    private val addActivityParticipationCountUseCase: AddActivityParticipationCountUseCase,
) {
    suspend operator fun invoke(
        gameId: Long,
        optionId: Long,
    ): BalanceGameResult {
        val result = balanceGameRepository.submitOption(
            gameId = gameId,
            optionId = optionId,
        )
        if (result.partnerChoice != null) addActivityParticipationCountUseCase()
        return result
    }
}
