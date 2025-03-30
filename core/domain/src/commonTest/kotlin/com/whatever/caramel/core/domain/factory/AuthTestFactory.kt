package com.whatever.caramel.core.domain.factory

import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.UserAuth
import com.whatever.caramel.core.domain.vo.user.UserStatus

object AuthTestFactory {
    fun createEmptyAuthToken() = AuthToken(
        accessToken = "",
        refreshToken = "",
    )

    fun createValidAuthToken() = AuthToken(
        accessToken = "valid_access_token",
        refreshToken = "valid_refresh_token",
    )

    fun createNewUserAuth(isEmptyToken: Boolean = false) = UserAuth(
        coupleId = null,
        userStatus = UserStatus.NEW,
        nickname = null,
        birthDayMillisecond = null,
        authToken = if (isEmptyToken) {
            createEmptyAuthToken()
        } else {
            createValidAuthToken()
        }
    )

    fun createSingleUserAuth(isEmptyToken: Boolean = false) = UserAuth(
        coupleId = null,
        userStatus = UserStatus.SINGLE,
        nickname = "nickname",
        birthDayMillisecond = 123456789L,
        authToken = if (isEmptyToken) {
            createEmptyAuthToken()
        } else {
            createValidAuthToken()
        }
    )

    fun createCoupleUserAuth(isEmptyToken: Boolean = false) = UserAuth(
        coupleId = 123L,
        userStatus = UserStatus.COUPLED,
        nickname = "nickname",
        birthDayMillisecond = 123456789L,
        authToken = if (isEmptyToken) {
            createEmptyAuthToken()
        } else {
            createValidAuthToken()
        }
    )
}