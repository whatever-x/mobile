package com.whatever.caramel.core.testing.domain

import com.whatever.caramel.core.domain.entity.User
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

    fun createSingleUserAuth(isValidToken: Boolean = true) = UserAuth(
        coupleId = null,
        userStatus = UserStatus.SINGLE,
        nickname = "test_user",
        birthDayMillisecond = 123456789L,
        authToken = if (isValidToken) {
            createEmptyAuthToken()
        } else {
            createValidAuthToken()
        }
    )

    fun createNewUserAuth(isValidToken: Boolean = true) = UserAuth(
        coupleId = null,
        userStatus = UserStatus.NEW,
        nickname = null,
        birthDayMillisecond = null,
        authToken = if (isValidToken) {
            createEmptyAuthToken()
        } else {
            createValidAuthToken()
        }
    )

    fun createCoupleUserAuth(isValidToken: Boolean = true) = UserAuth(
        coupleId = 123L,
        userStatus = UserStatus.COUPLED,
        nickname = "test_user",
        birthDayMillisecond = 123456789L,
        authToken = if (isValidToken) {
            createEmptyAuthToken()
        } else {
            createValidAuthToken()
        }
    )

    fun createInvalidUserAuth(isValidToken: Boolean = true) = UserAuth(
        coupleId = null,
        userStatus = UserStatus.COUPLED,
        nickname = "test_user",
        birthDayMillisecond = 123456789L,
        authToken = if (isValidToken) {
            createEmptyAuthToken()
        } else {
            createValidAuthToken()
        }
    )

    fun createDefaultUserAuth() = UserAuth(
        userStatus = UserStatus.NONE,
        authToken = createEmptyAuthToken()
    )

    fun createSocialLoginScenario() = listOf(
        createNewUserAuth(),
        createSingleUserAuth(),
        createCoupleUserAuth()
    )
}