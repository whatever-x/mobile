package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.event.AnalyticsEvent
import com.whatever.caramel.core.domain.event.AnalyticsEventBus
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.user.UserStatus

class RefreshUserSessionUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val analyticsEventBus: AnalyticsEventBus,
) {
    suspend operator fun invoke(): UserStatus {
        val localSavedToken = authRepository.readAuthToken()
        val newToken = authRepository.refreshAuthToken(localSavedToken)
        authRepository.setAuthToken(newToken)

        val userInfo = userRepository.getUserInfo()
        val userStatus = userInfo.userStatus
        userRepository.setUserStatus(status = userStatus)

        analyticsEventBus.emit(event = AnalyticsEvent.SetUserId(id = userInfo.id.toString()))

        return userStatus
    }
}
