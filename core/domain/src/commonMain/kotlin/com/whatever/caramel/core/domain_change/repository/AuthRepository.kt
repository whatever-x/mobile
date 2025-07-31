package com.whatever.caramel.core.domain_change.repository

import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.domain.model.user.UserAuth

interface AuthRepository {
    suspend fun loginWithSocialPlatform(
        idToken: String,
        socialLoginType: SocialLoginType,
    ): UserAuth

    suspend fun refreshAuthToken(oldToken: AuthToken): AuthToken

    suspend fun saveTokens(authToken: AuthToken)

    suspend fun getAuthToken(): AuthToken

    suspend fun deleteToken()

    suspend fun signOut()
}
