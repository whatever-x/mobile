package com.whatever.caramel.core.domain.usecase.auth

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AuthErrorCode
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.domain.vo.auth.UserAuth
import com.whatever.caramel.core.domain.vo.user.UserStatus

class SignInWithSocialPlatformUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        idToken: String,
        socialLoginType: SocialLoginType
    ): UserStatus {
        if (idToken.isEmpty()) {
            throw CaramelException(
                code = AuthErrorCode.EMPTY_ID_TOKEN,
                message = "IdToken이 빈 값입니다.",
                debugMessage = "ID Token is empty"
            )
        }
        val signInUserAuth: UserAuth = authRepository.loginWithSocialPlatform(
            idToken = idToken,
            socialLoginType = socialLoginType
        )
        if (signInUserAuth.authToken.refreshToken.isEmpty() ||
            signInUserAuth.authToken.refreshToken.isEmpty()
        ) {
            throw CaramelException(
                code = AuthErrorCode.EMPTY_AUTH_TOKEN,
                message = "서버에서 토큰 값을 받아오지 못했습니다",
                debugMessage = "Failed to retrieve token value from server"
            )
        }
        with(signInUserAuth) {
            authRepository.saveTokens(authToken = authToken)
            userRepository.setUserStatus(userStatus)
        }
        return signInUserAuth.userStatus
    }
}