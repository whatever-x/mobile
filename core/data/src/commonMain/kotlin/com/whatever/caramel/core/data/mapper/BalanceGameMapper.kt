package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.BalanceGame
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameResult
import com.whatever.caramel.core.remote.dto.balanceGame.OptionInfoDto
import com.whatever.caramel.core.remote.dto.balanceGame.response.BalanceGameResponse
import kotlinx.datetime.LocalDate

fun BalanceGameResponse.toBalanceGameResult(): BalanceGameResult =
    BalanceGameResult(
        gameInfo =
            BalanceGame(
                id = this.gameInfo.id,
                date = LocalDate.parse(this.gameInfo.date),
                question = this.gameInfo.question,
                options = this.gameInfo.options.map { it.toBalanceGameOption() },
            ),
        myChoice = this.myChoice?.toBalanceGameOption(),
        partnerChoice = this.partnerChoice?.toBalanceGameOption(),
    )

fun OptionInfoDto.toBalanceGameOption(): BalanceGame.Option =
    BalanceGame.Option(
        optionId = this.optionId,
        text = this.text,
    )
