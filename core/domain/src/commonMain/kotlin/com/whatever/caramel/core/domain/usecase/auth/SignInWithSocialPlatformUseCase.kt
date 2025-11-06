package com.whatever.caramel.core.domain.usecase.auth

import com.whatever.caramel.core.domain.event.AnalyticsEventBus
import com.whatever.caramel.core.domain.event.AnalyticsUserLifecycleEvent
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.auth.AuthResult
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.domain.vo.user.UserStatus

class SignInWithSocialPlatformUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val coupleRepository: CoupleRepository,
    private val analyticsEventBus: AnalyticsEventBus,
) {
    suspend operator fun invoke(
        idToken: String,
        socialLoginType: SocialLoginType,
    ): UserStatus {
        val signInUserAuth: AuthResult =
            authRepository.loginWithSocialPlatform(
                idToken = idToken,
                socialLoginType = socialLoginType,
            )

        with(signInUserAuth) {
            authRepository.setAuthToken(authToken = authToken)
            userRepository.setUserStatus(userStatus)

            if (coupleId != null) {
                coupleRepository.setCoupleId(coupleId = coupleId)
            }

            analyticsEventBus.emit(
                event =
                    AnalyticsUserLifecycleEvent.SignIn(
                        userId = userId.toString(),
                    ),
            )
        }

        return signInUserAuth.userStatus
    }
}
