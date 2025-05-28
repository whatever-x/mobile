package com.whatever.caramel.core.domain.usecase.balanceGame

import com.whatever.caramel.core.domain.repository.BalanceGameRepository
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameResult

class SubmitBalanceGameChoiceUseCase(
    private val balanceGameRepository: BalanceGameRepository
) {
    suspend operator fun invoke(optionId: Long): BalanceGameResult {
        val result = balanceGameRepository.submitOption(optionId = optionId)

        return result
    }
}