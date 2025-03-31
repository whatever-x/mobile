package com.whatever.caramel.core.testing.util

import kotlin.test.assertEquals

fun assertEqualsWithMessage(
    expected: Any, actual: Any
) = assertEquals(
    expected = expected,
    actual = actual,
    message = "테스트 실패에 실패했습니다.\n기대값 : ${expected}, 실제값 : $actual"
)