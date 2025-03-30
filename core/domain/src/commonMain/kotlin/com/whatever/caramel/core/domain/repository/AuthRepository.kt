package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.auth.UserAuth
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType

interface AuthRepository {
    suspend fun loginWithSocialPlatform(inputModel: SocialLoginInputModel)  : UserAuthAggregation
    suspend fun refreshAuthToken(oldToken: AuthToken) : AuthToken
    suspend fun saveTokens(authToken: AuthToken)
    suspend fun getAuthToken() : AuthToken
}