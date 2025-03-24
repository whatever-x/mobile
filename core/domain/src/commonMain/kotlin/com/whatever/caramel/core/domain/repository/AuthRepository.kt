package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.model.aggregate.UserAuthAggregation
import com.whatever.caramel.core.domain.model.auth.AuthToken
import com.whatever.caramel.core.domain.usecase.auth.SocialLoginInputModel

interface AuthRepository {
    suspend fun loginWithSocialPlatform(inputModel: SocialLoginInputModel)  : UserAuthAggregation
    suspend fun refreshAuthToken(oldToken: AuthToken)
    suspend fun saveTokens(accessToken : String, refreshToken : String)
    suspend fun getAuthToken() : AuthToken
}