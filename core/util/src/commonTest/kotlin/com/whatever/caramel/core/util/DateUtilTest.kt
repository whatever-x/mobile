package com.whatever.caramel.core.util

import kotlin.test.Test
import kotlin.test.assertEquals

class DateUtilTest {
    private val VALID_MILLISECOND: Long = 1744416000000 // 2025년 04월 12일

    @Test
    fun `변환 가능한 Millisecond라면 toFormattedDate은 구분자를 포함해서 날짜를 생성한다`() {
        assertEquals(
            expected = "2025-04-12",
            actual = VALID_MILLISECOND.toFormattedDate()
        )

        assertEquals(
            expected = "1970-01-01",
            actual = 0L.toFormattedDate()
        )
    }

    @Test
    fun `createDateFormat은 날짜가 아닌 다른 문자열이라면 빈값을 반환한다`() {
        // 날짜 형식이 아님
        assertEquals(
            expected = "",
            actual = createDateFormat(
                year = "1234",
                month = "56",
                day = "177"
            )
        )
        // 일이 없음
        assertEquals(
            expected = "",
            actual = createDateFormat(
                year = "2025",
                month = "04",
                day = ""
            )
        )
        // 2월은 31일이 없음
        assertEquals(
            expected = "",
            actual = createDateFormat(
                year = "2025",
                month = "02",
                day = "31"
            )
        )
    }

    @Test
    fun `createDateFormat은 년월일에 올바른 값이 적용됐다면 날짜형식으로 반환한다`() {
        assertEquals(
            expected = "2025-04-09",
            actual = createDateFormat(
                year = "2025",
                month = "4",
                day = "9"
            )
        )
        assertEquals(
            expected = "2025-04-09",
            actual = createDateFormat(
                year = "2025",
                month = "04",
                day = "09"
            )
        )
        assertEquals(
            expected = "2025-12-13",
            actual = createDateFormat(
                year = "2025",
                month = "12",
                day = "13"
            )
        )
    }

    @Test
    fun `toMillisecond는 올바른 날짜가 들어온다면 밀리초로 변환된다`() {
        assertEquals(
            expected = VALID_MILLISECOND,
            actual = "20250412".toMillisecond()
        )

        assertEquals(
            expected = VALID_MILLISECOND,
            actual = "20250412T00:00:00.000Z".toMillisecond()
        )

        assertEquals(
            expected = VALID_MILLISECOND,
            actual = "2025.04.12".toMillisecond()
        )

        assertEquals(
            expected = VALID_MILLISECOND,
            actual = "2025-04-12".toMillisecond()
        )
    }

    @Test
    fun `toMillisecond에 올바른 날짜형식이 아니라면 0L을 반환한다`() {
        // 올바른 날짜 형식 아님
        assertEquals(
            expected = 0L,
            actual = "20254999".toMillisecond()
        )
        assertEquals(
            expected = 0L,
            actual = "202549".toMillisecond()
        )
        assertEquals(
            expected = 0L,
            actual = "20254999T00:00:00.000Z".toMillisecond()
        )
        assertEquals(
            expected = 0L,
            actual = "adfdnfdlkT00:00:00.000Z".toMillisecond()
        )
        assertEquals(
            expected = 0L,
            actual = "2025-4-12".toMillisecond()
        )
    }
}

