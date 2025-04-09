package com.whatever.caramel.core.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * 밀리초를 날짜 문자열로 변환
 * @param separator 날짜 문자열의 구분자 (기본값: '-')
 * @return 밀리초를 날짜 문자열로 변환한 결과
 * @exception IllegalArgumentException 밀리초를 날짜로 파싱하지 못하면 빈값을 반환
 * */
fun Long.toFormattedDate(separator: String = "-") = try {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val year = localDateTime.year.toString().padStart(4, '0')
    val month = localDateTime.monthNumber.toString().padStart(2, '0')
    val day = localDateTime.dayOfMonth.toString().padStart(2, '0')

    "$year$separator$month$separator$day"
} catch (e: Exception) {
    ""
}

/**
 * 년, 월, 일을 Date 형식으로 변환 (구분자 '-')
 * @param year 년
 * @param month 월
 * @param day 일
 * @exception IllegalStateException 유효하지 않는 문자열
 * @return 밀리초를 날짜 문자열로 변환한 결과
 * */
fun createDateFormat(
    year: String,
    month: String,
    day: String,
) = try {
    check(year.toInt() in 1..9999 && month.toInt() in 1..12 && day.toInt() in 1..31) { "날짜 형식이 잘못되었습니다." }

    val localDate = LocalDate(year.toInt(), month.toInt(), day.toInt())
    val formattedMonth = localDate.monthNumber.toString().padStart(2, '0')
    val formattedDay = localDate.dayOfMonth.toString().padStart(2, '0')
    "$year-$formattedMonth-$formattedDay"
} catch (e: Exception) {
    ""
}

/**
 * 날짜 문자열을 밀리초로 변환
 * Timezone형식으로 사용한다면 해당 Timezone을 기준으로 변환, Timezone이 존재하지 않는다면 시스템 기본 Timezone을 사용.
 * @return 현재 날짜 기준의 밀리초
 * */
fun String.toMillisecond(): Long = try {
    val timeZone = this.extractTimeZonePart() ?: "T00:00:00.000Z"

    val rawDate = this.replace(Regex("[^0-9]"), "")
    val year = rawDate.substring(0, 4)
    val month = rawDate.substring(4, 6)
    val day = rawDate.substring(6, 8)

    val localDate = LocalDate(year.toInt(), month.toInt(), day.toInt())
    val formattedMonth = localDate.monthNumber.toString().padStart(2, '0')
    val formattedDay = localDate.dayOfMonth.toString().padStart(2, '0')
    val dateStr = "${localDate.year}-${formattedMonth}-${formattedDay}${timeZone}"
    Instant.parse(dateStr).toEpochMilliseconds()
} catch (e: Exception) {
    0L
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