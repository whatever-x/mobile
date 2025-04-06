package com.whatever.caramel.core.testing.factory

import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.UserAuth
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.testing.constants.TestAuthInfo
import com.whatever.caramel.core.testing.constants.TestCoupleInfo
import com.whatever.caramel.core.testing.constants.TestUserInfo

object AuthTestFactory {
    fun createExpiredToken() = AuthToken(
        accessToken = TestAuthInfo.EXPIRED_ACCESS_TOKEN,
        refreshToken = TestAuthInfo.EXPIRED_REFRESH_TOKEN
    )

    fun createValidToken() = AuthToken(
        accessToken = TestAuthInfo.VALID_ACCESS_TOKEN,
        refreshToken = TestAuthInfo.VALID_REFRESH_TOKEN
    )

    fun createSingleUserAuth() = UserAuth(
        coupleId = null,
        userStatus = UserStatus.SINGLE,
        nickname = TestUserInfo.TEST_USER_NICKNAME,
        birthDayMillisecond = TestUserInfo.TEST_BIRTH_DAY_MILLISECOND,
        authToken = createValidToken()
    )

    fun createNewUserAuth() = UserAuth(
        coupleId = null,
        userStatus = UserStatus.NEW,
        nickname = null,
        birthDayMillisecond = null,
        authToken = createValidToken()
    )

    fun createCoupleUserAuth() = UserAuth(
        coupleId = TestCoupleInfo.TEST_COUPLE_ID,
        userStatus = UserStatus.COUPLED,
        nickname = TestUserInfo.TEST_USER_NICKNAME,
        birthDayMillisecond = TestUserInfo.TEST_BIRTH_DAY_MILLISECOND,
        authToken = createValidToken()
    )
}