package com.whatever.caramel.core.ui.picker.model

import com.whatever.caramel.core.util.DateUtil

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
    }
}