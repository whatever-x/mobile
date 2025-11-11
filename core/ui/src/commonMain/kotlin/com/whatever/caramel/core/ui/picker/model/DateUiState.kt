package com.whatever.caramel.core.ui.picker.model

import com.whatever.caramel.core.util.DateUtil
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

data class DateUiState(
    val year: Int,
    val month: Int,
    val day: Int,
) {
    companion object {
        fun currentDate(): DateUiState {
            val today = DateUtil.today()

            return DateUiState(
                year = today.year,
                month = today.month.number,
                day = today.day,
            )
        }

        fun get(dateString: String): DateUiState =
            runCatching {
                val dateNumber = dateString.replace(Regex("[^0-9]"), "")
                val year = dateNumber.substring(0, 4).toInt()
                val month = dateNumber.substring(4, 6).toInt()
                val day = dateNumber.substring(6, 8).toInt()
                DateUiState(year, month, day)
            }.getOrNull() ?: currentDate()

        fun from(dateTime: LocalDateTime): DateUiState =
            DateUiState(
                year = dateTime.year,
                month = dateTime.month.number,
                day = dateTime.day,
            )
    }
}

fun DateUiState.toLocalDate(): LocalDate = LocalDate(year, month, day)
