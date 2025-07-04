package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.vo.couple.CoupleStatus
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime

data class Couple(
    val id: Long,
    val startDate: String,
    val sharedMessage: String,
    val status: CoupleStatus,
) {
    val daysTogether: Int
        get() {
            try {
                val (year, month, day) = startDate.split(".").map { it.toInt() }
                val startDate = LocalDate(year, month, day)
                val today =
                    Clock.System
                        .now()
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                        .date
                return startDate.daysUntil(today) + 1
            } catch (e: Exception) {
                return 0
            }
        }
}
