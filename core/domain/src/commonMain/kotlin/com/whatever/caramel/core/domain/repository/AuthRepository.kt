package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.auth.UserAuth
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType

interface AuthRepository {
    suspend fun loginWithSocialPlatform(
        idToken : String,
        socialLoginType: SocialLoginType
    )  : UserAuth
    suspend fun refreshAuthToken(oldToken: AuthToken) : AuthToken
    suspend fun saveTokens(authToken: AuthToken)
    suspend fun getAuthToken() : AuthToken
    suspend fun deleteToken()
}