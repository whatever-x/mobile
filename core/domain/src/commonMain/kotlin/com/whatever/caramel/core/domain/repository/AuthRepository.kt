package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.model.aggregate.UserAuth
import com.whatever.caramel.core.domain.model.auth.AuthToken
import com.whatever.caramel.core.domain.usecase.auth.SocialLoginInputModel

interface AuthRepository {
    suspend fun loginWithSocialPlatform(inputModel: SocialLoginInputModel)  : UserAuth
    suspend fun refreshAuthToken(): AuthToken
    suspend fun saveTokens(accessToken : String, refreshToken : String)
}