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
            return try {
                val parts = startDate.split(".")
                val year = parts[0].toInt()
                val month = parts[1].toInt()
                val day = parts[2].toInt()

                val startDate = LocalDate(year, month, day)
                val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
                startDate.daysUntil(today)
            } catch (e: Exception) {
                throw e
            }
        }
}