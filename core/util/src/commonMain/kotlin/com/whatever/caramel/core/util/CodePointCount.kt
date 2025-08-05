package com.whatever.caramel.core.util

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
