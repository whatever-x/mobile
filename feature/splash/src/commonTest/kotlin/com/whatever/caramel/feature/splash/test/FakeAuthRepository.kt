package com.whatever.caramel.feature.splash.test

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AuthErrorCode
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.domain.vo.auth.UserAuth
import com.whatever.caramel.core.testing.util.TestMessage

class FakeAuthRepository : AuthRepository {
    var saveAuthToken: AuthToken? = null
    var refreshAuthTokenResponse: AuthToken? = null
    var isRefreshFail = false

    override suspend fun loginWithSocialPlatform(
        idToken: String,
        socialLoginType: SocialLoginType
    ): UserAuth {
        throw UnsupportedOperationException(TestMessage.NOT_USE_IN_TEST)
    }

    override suspend fun refreshAuthToken(oldToken: AuthToken): AuthToken {
        if(isRefreshFail){
            throw CaramelException(
                code = AuthErrorCode.UNAUTHORIZED,
                message = SplashViewModelTest.REFRESH_TOKEN_ERROR_MSG
            )
        }
        return refreshAuthTokenResponse
            ?: throw CaramelException(
                code = NetworkErrorCode.UNKNOWN,
                message = TestMessage.REQUIRE_INIT_FOR_TEST
            )
    }

    override suspend fun saveTokens(authToken: AuthToken) {
        saveAuthToken = authToken
    }

    override suspend fun getAuthToken(): AuthToken {
        return saveAuthToken ?: throw CaramelException(
            code = NetworkErrorCode.UNKNOWN,
            message = TestMessage.REQUIRE_INIT_FOR_TEST
        )
    }
}