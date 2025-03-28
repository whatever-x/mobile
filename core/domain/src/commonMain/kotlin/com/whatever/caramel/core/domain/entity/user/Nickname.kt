package com.whatever.caramel.core.domain.entity.user

import com.whatever.caramel.core.domain.exception.code.AppExceptionCode
import com.whatever.caramel.core.domain.exception.CaramelException
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class Nickname(private val value: String) {
    init {
        if (value.length < MIN_LENGTH || value.length > MAX_LENGTH) {
            throw CaramelException(
                code = AppExceptionCode.INVALID_PARAMS,
                message = "닉네임은 ${MIN_LENGTH}자에서 ${MAX_LENGTH}자 사이로 입력해야합니다.",
                debugMessage = "input value = $value"
            )
        }
        if (!value.matches(VALID_PATTERN)) {
            throw CaramelException(
                code = AppExceptionCode.INVALID_PARAMS,
                message = "닉네임에 특수문자 또는 공백을 포함할 수 없습니다.",
                debugMessage = "input value = $value"
            )
        }
    }

    companion object {
        private const val MIN_LENGTH = 0
        private const val MAX_LENGTH = 8
        private val VALID_PATTERN = Regex("^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]*\$")
    }

    override fun toString() = value
}