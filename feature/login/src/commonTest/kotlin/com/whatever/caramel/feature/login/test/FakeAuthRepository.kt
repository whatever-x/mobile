package com.whatever.caramel.feature.login.test

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.domain.vo.auth.UserAuth
import com.whatever.caramel.core.testing.util.TestMessage

class FakeAuthRepository : AuthRepository {
    var isInvalidIdToken = false
    var loginWithSocialPlatformResponse: UserAuth? = null
    private var storedAuthToken: AuthToken? = null

    override suspend fun loginWithSocialPlatform(
        idToken: String,
        socialLoginType: SocialLoginType
    ): UserAuth {
        if (isInvalidIdToken) {
            throw CaramelException(
                code = NetworkErrorCode.INVALID_ARGUMENT,
                message = LoginViewModelTest.NETWORK_INVALID_TOKEN_ERROR_MSG
            )
        }
        return loginWithSocialPlatformResponse
            ?: throw CaramelException(
                code = NetworkErrorCode.UNKNOWN,
                message = TestMessage.REQUIRE_INIT_FOR_TEST
            )
    }

    override suspend fun refreshAuthToken(oldToken: AuthToken): AuthToken {
        throw UnsupportedOperationException(TestMessage.NOT_USE_IN_TEST)
    }

    override suspend fun saveTokens(authToken: AuthToken) {
        storedAuthToken = authToken
    }

    override suspend fun getAuthToken(): AuthToken {
        return storedAuthToken
            ?: throw CaramelException(
                code = NetworkErrorCode.UNKNOWN,
                message = TestMessage.REQUIRE_INIT_FOR_TEST
            )
    }
}