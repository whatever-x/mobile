package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.auth.AuthResult
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType

interface AuthRepository {
    suspend fun loginWithSocialPlatform(
        idToken: String,
        socialLoginType: SocialLoginType,
    ): AuthResult

    suspend fun refreshAuthToken(oldToken: AuthToken): AuthToken

    suspend fun setAuthToken(authToken: AuthToken)

    suspend fun readAuthToken(): AuthToken

    suspend fun removeAuthToken()

    suspend fun signOut()

    // @ham2174 TODO : logOut 함수 정의
}
