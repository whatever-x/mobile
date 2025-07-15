package com.whatever.caramel.core.ui.util

/**
 * [text]가 한 줄이고,
 * 이모지를 포함한 [text] 기준으로 최대 [limitLength] 이하인지 검사한 뒤,
 * 각 상황에 맞는 콜백을 실행한다.
 *
 * @param text 검사할 텍스트
 * @param limitLength 최대 허용 글자 수
 * @param onPass 조건을 만족하면 호출되는 콜백
 * @param onContainsNewline 줄바꿈 문자가 포함되면 호출되는 콜백
 * @param onExceedLimit 최대 글자수를 초과하면 호출되는 콜백
 * @author GunHyung Ham
 */
fun validateInputText(
    text: String,
    limitLength: Int,
    onPass: (String) -> Unit,
    onContainsNewline: () -> Unit = { },
    onExceedLimit: () -> Unit = { },
) {
    when {
        text.contains("\n") -> {
            onContainsNewline()
        }
        Regex("\\X").findAll(text).count() > limitLength -> {
            onExceedLimit()
        }
        else -> {
            onPass(text)
        }
    }
}