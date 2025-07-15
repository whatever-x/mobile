package com.whatever.caramel.core.util

import kotlinx.datetime.LocalDateTime

object TimeUtil {
    /**
     * 현재 LocalDateTime을 5분 단위로 반올림해서 반환합니다.
     *
     * @author GunHyung-Ham
     * @param dateTime LocalDateTime 타입
     * @return 5분 단위로 반올림된 LocalDateTime (초와 나노초는 0으로 초기화)
     */
    fun roundToNearest5Minutes(dateTime: LocalDateTime): LocalDateTime {
        val roundedMinute = ((dateTime.minute + 2) / 5) * 5
        val adjustedHour = if (roundedMinute >= 60) dateTime.hour + 1 else dateTime.hour
        val finalMinute = if (roundedMinute >= 60) 0 else roundedMinute

        return dateTime.copy(
            hour = adjustedHour,
            minute = finalMinute,
            second = 0,
            nanosecond = 0,
        )
    }
}
