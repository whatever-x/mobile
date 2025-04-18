package com.whatever.caramel.core.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateFormatter {

    /**
     * 밀리초를 날짜 문자열로 변환
     * @param separator 날짜 문자열의 구분자 (기본값: '-')
     * @return 밀리초를 날짜 문자열로 변환한 결과, 실패 시 null
     * @author RyuSw-cs
     * */
    fun Long.toFormattedDate(separator: String = "-"): String? =
        try {
            val instant = Instant.fromEpochMilliseconds(this)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

            val year = localDateTime.year.toString().padStart(4, '0')
            val month = localDateTime.monthNumber.toString().padStart(2, '0')
            val day = localDateTime.dayOfMonth.toString().padStart(2, '0')

            "$year$separator$month$separator$day"
        } catch (e: Exception) {
            null
        }

    /**
     * 년, 월, 일을 Date 형식으로 변환 (구분자 '-')
     * @param year 년
     * @param month 월
     * @param day 일
     * @return 밀리초를 날짜 문자열로 변환한 결과, 변환 실패 시 빈 문자열
     * @author RyuSw-cs
     * */
    fun createDateString(year: Int, month: Int, day: Int): String =
        try {
            check(year in 1..9999 && month in 1..12 && day in 1..31) { "날짜 형식이 잘못되었습니다." }

            val localDate = LocalDate(year, month, day)
            val formattedMonth = localDate.monthNumber.toString().padStart(2, '0')
            val formattedDay = localDate.dayOfMonth.toString().padStart(2, '0')
            "$year-$formattedMonth-$formattedDay"
        } catch (e: Exception) {
            ""
        }

}