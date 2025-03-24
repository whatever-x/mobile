package com.whatever.caramel.core.domain.entity.user

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class Nickname(private val value: String) {
    companion object {
        private const val MIN_LENGTH = 1
        private const val MAX_LENGTH = 8
        private val VALID_PATTERN = Regex("^[a-zA-Z0-9가-힣]+$")

        fun check(value: String) {
            if (value.length < MIN_LENGTH || value.length > MAX_LENGTH) {
                throw CaramelException(
                    message = "입력한 닉네임의 길이는 $MIN_LENGTH 자에서 $MAX_LENGTH 자 사이여야 합니다.",
                    debugMessage = "닉네임 길이 검증 실패",
                    errorUiType = ErrorUiType.SNACK_BAR
                )
            }
            if (!VALID_PATTERN.matches(value)) {
                throw CaramelException(
                    message = "입력한 닉네임에 대한 검증이 실패했습니다.",
                    debugMessage = "닉네임 형식 검증 실패",
                    errorUiType = ErrorUiType.SNACK_BAR
                )
            }
        }

        fun create(value: String, unChecked: Boolean = false): Nickname {
            if (!unChecked) {
                check(value)
            }
            return Nickname(value)
        }
    }

    override fun toString() = value
}