package com.whatever.caramel.core.testing.repository

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AuthErrorCode
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.domain.vo.auth.UserAuth
import com.whatever.caramel.core.testing.constant.TestMessage

class TestAuthRepository : AuthRepository {
    var isRefreshFail = false
    var isInvalidIdToken = false
    var saveAuthToken: AuthToken? = null
    var refreshAuthTokenResponse: AuthToken? = null
    var loginWithSocialPlatformResponse: UserAuth? = null

    override suspend fun loginWithSocialPlatform(
        idToken: String,
        socialLoginType: SocialLoginType
    ): UserAuth {
        if (isInvalidIdToken) {
            throw CaramelException(
                code = NetworkErrorCode.INVALID_ARGUMENT,
                message = TestMessage.INVALID_ARGUMENT
            )
        }
        return loginWithSocialPlatformResponse
            ?: throw CaramelException(
                code = NetworkErrorCode.UNKNOWN,
                message = TestMessage.REQUIRE_INIT_FOR_TEST
            )
    }

    override suspend fun refreshAuthToken(oldToken: AuthToken): AuthToken {
        if(isRefreshFail){
            throw CaramelException(
                code = AuthErrorCode.UNAUTHORIZED,
                message = TestMessage.TOKEN_REFRESH_FAIL
            )
        }
        return refreshAuthTokenResponse
            ?: throw CaramelException(
                code = NetworkErrorCode.UNKNOWN,
                message = TestMessage.UNKNOWN_ERROR
            )
    }

    override suspend fun saveTokens(authToken: AuthToken) {
        saveAuthToken = authToken
    }

    override suspend fun getAuthToken(): AuthToken {
        return saveAuthToken ?: throw CaramelException(
            code = NetworkErrorCode.UNKNOWN,
            message = TestMessage.UNKNOWN_ERROR
        )
    }
}