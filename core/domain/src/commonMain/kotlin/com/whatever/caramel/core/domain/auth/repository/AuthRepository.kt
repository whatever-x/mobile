package com.whatever.caramel.core.domain.auth.repository

import com.whatever.caramel.core.domain.auth.model.SocialLoginModel

interface AuthRepository {

    suspend fun loginWithSocialPlatform(socialLoginModel: SocialLoginModel)

}