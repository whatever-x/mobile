package com.whatever.caramel.core.domain.usecase.auth

import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.domain.vo.auth.UserAuth
import com.whatever.caramel.core.domain.vo.user.UserStatus

class SignInWithSocialPlatformUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val coupleRepository: CoupleRepository
) {
    suspend operator fun invoke(
        idToken: String,
        socialLoginType: SocialLoginType
    ) {
        val signInUserAuth: UserAuth = authRepository.loginWithSocialPlatform(
            idToken = idToken,
            socialLoginType = socialLoginType
        )
        with(signInUserAuth) {
            authRepository.saveTokens(authToken = authToken)
            userRepository.setUserStatus(userStatus)

            if (coupleId != null) {
                coupleRepository.setCoupleId(coupleId = coupleId)
            }
        }
    }
}