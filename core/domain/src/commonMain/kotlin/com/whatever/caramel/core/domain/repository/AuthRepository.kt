package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.auth.AuthResult
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.domain.vo.user.RefreshUserSessionResult

interface AuthRepository {
    suspend fun loginWithSocialPlatform(
        idToken: String,
        socialLoginType: SocialLoginType,
    ): AuthResult

    suspend fun refreshAuthToken(oldToken: AuthToken): RefreshUserSessionResult

    suspend fun setAuthToken(authToken: AuthToken)

    suspend fun readAuthToken(): AuthToken

    suspend fun removeAuthToken()

    suspend fun signOut()

    suspend fun logOut()
}
