package com.whatever.caramel.core.domain.legacy.core_util

// 유틸성 확장함수, 유틸로 옮기는건?
// CaramelException을 활용하기 위해 Domain으로 이동했다고 알고있는데 현재 보이지 않음
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
