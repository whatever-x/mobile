package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.BalanceGame
import com.whatever.caramel.core.domain.entity.BalanceGameOption
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameResult
import com.whatever.caramel.core.remote.dto.balanceGame.OptionInfoDto
import com.whatever.caramel.core.remote.dto.balanceGame.response.BalanceGameResponse
import kotlinx.datetime.LocalDate

fun BalanceGameResponse.toBalanceGameResult(): BalanceGameResult =
    BalanceGameResult(
        gameInfo = BalanceGame(
            id = this.gameInfo.id,
            date = LocalDate.parse(this.gameInfo.date),
            question = this.gameInfo.question,
            options = this.gameInfo.options.map { it.toBalanceGameOption() },
        ),
        myChoice = BalanceGameOption(
            optionId = this.myChoice?.optionId ?: 0,
            text = this.myChoice?.text ?: ""
        ),
        partnerChoice = BalanceGameOption(
            optionId = this.partnerChoice?.optionId ?: 0,
            text = this.partnerChoice?.text ?: ""
        ),
    )

fun OptionInfoDto.toBalanceGameOption(): BalanceGameOption =
    BalanceGameOption(
        optionId = this.optionId,
        text = this.text
    )
