package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.model.aggregate.UserAuthAggregation
import com.whatever.caramel.core.domain.model.auth.AuthToken
import com.whatever.caramel.core.domain.usecase.auth.SocialLoginInputModel

interface AuthRepository {
    suspend fun loginWithSocialPlatform(inputModel: SocialLoginInputModel)  : UserAuthAggregation
    suspend fun refreshAuthToken(oldToken: AuthToken) : AuthToken
    suspend fun saveTokens(authToken: AuthToken)
    suspend fun getAuthToken() : AuthToken
}