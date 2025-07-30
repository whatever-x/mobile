package com.whatever.caramel.core.domain.entity

import kotlinx.datetime.LocalDate

data class BalanceGame(
    val id: Long,
    val date: LocalDate,
    val question: String,
    val options: List<BalanceGameOption>,
) {
    data class BalanceGameOption(
        val optionId: Long,
        val text: String,
    )
}
