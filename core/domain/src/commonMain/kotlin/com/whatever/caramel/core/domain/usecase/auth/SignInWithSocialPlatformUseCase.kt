package com.whatever.caramel.core.domain.usecase.auth

import com.whatever.caramel.core.domain.entity.auth.SocialLoginType
import com.whatever.caramel.core.domain.entity.user.UserStatus
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository

data class SocialLoginInputModel(
    val idToken : String,
    val socialLoginType: SocialLoginType
)

class SignInWithSocialPlatformUseCase (
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
){
    suspend operator fun invoke(inputModel : SocialLoginInputModel) : UserStatus {
        val loginResponse = authRepository.loginWithSocialPlatform(inputModel)
        with(loginResponse){
            authRepository.saveTokens(accessToken = authToken.accessToken, refreshToken = authToken.refreshToken)
            userRepository.setUserStatus(userBasic.userStatus)
        }
        return loginResponse.userBasic.userStatus
    }
}