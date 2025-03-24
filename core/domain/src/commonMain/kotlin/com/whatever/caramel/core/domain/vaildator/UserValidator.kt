package com.whatever.caramel.core.domain.vaildator

object UserValidator {
    private val NICKNAME_MAX_SIZE = 8

    fun checkNicknameSize(nickname: String): Boolean {
        return nickname.length <= NICKNAME_MAX_SIZE
    }
}