package com.whatever.caramel.core.data.mapper

import kotlinx.datetime.Instant

/**
 * "YYYY-MM-DD"에 대한 밀리초 변환
 * @return 변환 성공 시 해당 년원일에 대해 밀리초, 실패시 null
 * */
fun String.toTimezoneMillisecond() = try {
    Instant.parse(this + "T00:00:00.000Z").toEpochMilliseconds()
} catch (e: Exception) {
    null
}