package com.whatever.caramel.core.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

object DateParser {
    /**
     * 날짜 문자열을 밀리초로 변환
     * Timezone형식으로 사용한다면 해당 Timezone을 기준으로 변환, Timezone이 존재하지 않는다면 시스템 기본 Timezone을 사용.
     * @return 현재 날짜 기준의 밀리초, 변환 실패 시 null
     * */
    fun String.toMillisecond(): Long? =
        try {
            val timeZone = this.extractTimeZonePart() ?: "T00:00:00.000Z"

            val rawDate = this.replace(Regex("[^0-9]"), "")
            val year = rawDate.substring(0, 4)
            val month = rawDate.substring(4, 6)
            val day = rawDate.substring(6, 8)

            val localDate = LocalDate(year.toInt(), month.toInt(), day.toInt())
            val formattedMonth = localDate.monthNumber.toString().padStart(2, '0')
            val formattedDay = localDate.dayOfMonth.toString().padStart(2, '0')
            val dateStr = "${localDate.year}-$formattedMonth-${formattedDay}$timeZone"
            Instant.parse(dateStr).toEpochMilliseconds()
        } catch (e: Exception) {
            null
        }

    /**
     * 타임존 추출
     * @return 타임존 문자열, 없으면 null
     * */
    private fun String.extractTimeZonePart(): String? {
        val regex = Regex("(T.*?(Z|[+-]\\d{2}:?\\d{2}))")
        val matchResult = regex.find(this) ?: return null
        return matchResult.groupValues[1]
    }
}
