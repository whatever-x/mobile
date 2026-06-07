package com.whatever.caramel.feature.balancegame.history.model

import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameResult
import kotlinx.datetime.number

data class BalanceGameHistoryUiModel(
    val gameId: Long,
    val question: String,
    val dateText: String,
    val myChoiceText: String,
    val partnerChoiceText: String,
)

internal fun BalanceGameResult.toHistoryUiModel(): BalanceGameHistoryUiModel =
    BalanceGameHistoryUiModel(
        gameId = gameInfo.id,
        question = gameInfo.question,
        dateText =
            buildString {
                append(gameInfo.date.year.toString())
                append(".")
                append(
                    gameInfo.date.month.number
                        .toString()
                        .padStart(2, '0'),
                )
                append(".")
                append(
                    gameInfo.date.day
                        .toString()
                        .padStart(2, '0'),
                )
            },
        myChoiceText = myChoice?.text ?: "",
        partnerChoiceText = partnerChoice?.text ?: "",
    )
