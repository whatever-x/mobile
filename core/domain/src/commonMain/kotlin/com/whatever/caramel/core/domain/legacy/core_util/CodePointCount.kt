package com.whatever.caramel.core.domain.legacy.core_util

/**
 * 기존에 ui에 의존되는 기능이라 core:ui였지만 ContentVaildator에서 예외를 던지기 위해서 domain에 두었음
 * 구조를 다음과 같이변경하면 core:ui에서 사용가능
 * 1. ContentVaildator에서 check의 파라미터를 count로 변경
 * 2. ViewModel에서 현재 입력된 count를 core:ui의 codePointCount를 통해 계산
 * */
fun CharSequence.codePointCount(
    beginIndex: Int = 0,
    endIndex: Int = this.length,
): Int {
    if (beginIndex < 0 || endIndex > length || beginIndex > endIndex) throw IndexOutOfBoundsException()

    var index = beginIndex
    var count = 0
    while (index < endIndex) {
        val firstChar = this[index]
        index++
        if (firstChar.isHighSurrogate() && index < endIndex) {
            val nextChar = this[index]
            if (nextChar.isLowSurrogate()) {
                index++
            }
        }

        count++
    }
    return count
}
