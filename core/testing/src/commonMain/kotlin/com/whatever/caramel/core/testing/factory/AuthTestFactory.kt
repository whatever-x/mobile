package com.whatever.caramel.core.testing.factory

import com.whatever.caramel.core.domain.vo.auth.AuthResult
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.testing.constants.TestAuthInfo
import com.whatever.caramel.core.testing.constants.TestCoupleInfo
import com.whatever.caramel.core.testing.constants.TestUserInfo

object AuthTestFactory {
    fun createExpiredToken() =
        AuthToken(
            accessToken = TestAuthInfo.EXPIRED_ACCESS_TOKEN,
            refreshToken = TestAuthInfo.EXPIRED_REFRESH_TOKEN,
        )

    fun createValidToken() =
        AuthToken(
            accessToken = TestAuthInfo.VALID_ACCESS_TOKEN,
            refreshToken = TestAuthInfo.VALID_REFRESH_TOKEN,
        )

    fun createSingleUserAuth() =
        AuthResult(
            coupleId = null,
            userId = TestUserInfo.TEST_USER_ID,
            userStatus = UserStatus.SINGLE,
            authToken = createValidToken(),
        )

    fun createNewUserAuth() =
        AuthResult(
            coupleId = null,
            userId = TestUserInfo.TEST_USER_ID,
            userStatus = UserStatus.NEW,
            authToken = createValidToken(),
        )

    fun createCoupleUserAuth() =
        AuthResult(
            coupleId = TestCoupleInfo.TEST_COUPLE_ID,
            userId = TestUserInfo.TEST_USER_ID,
            userStatus = UserStatus.COUPLED,
            authToken = createValidToken(),
        )
}
