package com.whatever.caramel.core.ui.util

/**
 * 텍스트가 한 줄이고
 * 이모지를 포함하는 텍스트의 길이가 [limitLength] 이하인지 검사한다.
 *
 * @param text 검사할 텍스트
 * @param limitLength 최대 허용 글자수
 * @author GunHyung Ham
 */
fun isValidLimitedText(
    text: String,
    limitLength: Int
): Boolean {
    return !text.contains("\n") && Regex("\\X").findAll(text).count() <= limitLength
}