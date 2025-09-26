package com.whatever.caramel.core.ui.picker.model

import com.whatever.caramel.core.util.DateUtil
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class TimeUiState(
    val period: String,
    val hour: String,
    val minute: String,
) {
    companion object {
        fun currentTime(): TimeUiState {
            val now = DateUtil.todayLocalDateTime().time

            return TimeUiState(
                period = if (now.hour < 12) "오전" else "오후",
                hour = now.hour.toString(),
                minute = now.minute.toString(),
            )
        }

        fun from(dateTime: LocalDateTime): TimeUiState {
            val currentHour = dateTime.hour
            val period = if (currentHour < 12) "오전" else "오후"
            val hourIn12 = if (currentHour == 0 || currentHour == 12) 12 else currentHour % 12
            return TimeUiState(
                period = period,
                hour = hourIn12.toString(),
                minute = dateTime.minute.toString(),
            )
        }
    }
}

enum class Period(
    val value: String,
) {
    AM(value = "오전"),
    PM(value = "오후"),
}

fun TimeUiState.toLocalTime(): LocalTime =
    LocalTime(
        hour = when {
            period == Period.AM.value && hour == "12" -> 0
            period == Period.PM.value && hour == "12" -> 12
            period == Period.PM.value -> hour.toInt() + 12
            else -> hour.toInt()
        },
        minute = minute.toInt(),
    )
