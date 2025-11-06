package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.event.AnalyticsEventBus
import com.whatever.caramel.core.domain.event.AnalyticsUserLifecycleEvent
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.user.RefreshUserSessionResult
import com.whatever.caramel.core.domain.vo.user.UserStatus

class RefreshUserSessionUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val analyticsEventBus: AnalyticsEventBus,
) {
    suspend operator fun invoke(): UserStatus {
        val localSavedToken = authRepository.readAuthToken()
        val result: RefreshUserSessionResult =
            authRepository.refreshAuthToken(oldToken = localSavedToken)

        with(result) {
            authRepository.setAuthToken(authToken = authToken)
            userRepository.setUserStatus(status = userStatus)

            analyticsEventBus.emit(
                event =
                    AnalyticsUserLifecycleEvent.RefreshedUserSession(
                        userId = userId.toString(),
                    ),
            )
        }

        return result.userStatus
    }
}
