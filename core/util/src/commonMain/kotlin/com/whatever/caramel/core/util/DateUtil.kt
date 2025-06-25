package com.whatever.caramel.core.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateUtil {

    /**
     * 년, 월에 따른 마지막 일자 구하는 함수
     * @param year 년
     * @param month 월
     * @return 마지막 일자
     * @author GunHyung-Ham
     */
    fun getLastDayOfMonth(year: Int, month: Int): Int =
        when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (isLeapYear(year)) 29 else 28
            else -> 30
        }

    /**
     * 파라미터의 연도가 윤년인지 여부를 판단하는 함수
     * @param year 년
     * @return Boolean
     * @author GunHyung-Ham
     */
    private fun isLeapYear(year: Int): Boolean =
        (year % 4 == 0 && year % 100 != 0) || year % 400 == 0

    /**
     * 오늘 날짜를 구하는 함수
     * @return LocalDate 형식의 오늘 날짜
     * @author GunHyung-Ham
     */
    fun today(): LocalDate =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    /**
     * 오늘 날짜와 시간을 구하는 함수
     * @return LocalDateTime 형식의 오늘 날짜와 시간
     * @author evergreentree97
     */
    fun todayLocalDateTime(): LocalDateTime =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

}