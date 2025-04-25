package com.whatever.caramel.core.ui.picker.model

import com.whatever.caramel.core.util.DateFormatter.toLocalDateTime
import com.whatever.caramel.core.util.DateUtil

// @ham2174 FIXME : DateMonthPicker 또는 DatePicker 의 상태 변경시 DateUiState / DateMonthUiState 로 분리
data class DateUiState(
    val year: Int,
    val month: Int,
    val day: Int
) {
    companion object {
        fun currentDate(): DateUiState {
            val today = DateUtil.today()

            return DateUiState(
                year = today.year,
                month = today.monthNumber,
                day = today.dayOfMonth
            )
        }

        fun get(millisecond: Long) : DateUiState {
            val localDateTime = millisecond.toLocalDateTime()
            return DateUiState(
                year = localDateTime.year,
                month = localDateTime.monthNumber,
                day = localDateTime.dayOfMonth
            )
        }
    }
}