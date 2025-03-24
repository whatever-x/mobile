package com.whatever.caramel.core.domain.entity.user

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
                throw IllegalArgumentException("입력한 닉네임의 길이는 $MIN_LENGTH 자에서 $MAX_LENGTH 자 사이여야 합니다.")
            }
            if (!VALID_PATTERN.matches(value)) {
                throw IllegalArgumentException("닉네임 정규식 검증 실패")
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