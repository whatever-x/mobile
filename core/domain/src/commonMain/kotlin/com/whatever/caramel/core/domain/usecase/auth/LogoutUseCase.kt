package com.whatever.caramel.core.domain.usecase.auth

import com.whatever.caramel.core.domain.event.AnalyticsEventBus
import com.whatever.caramel.core.domain.event.AnalyticsUserLifecycleEvent
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.UserRepository

class LogoutUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val coupleRepository: CoupleRepository,
    private val analyticsEventBus: AnalyticsEventBus,
) {
    suspend operator fun invoke() {
        authRepository.logOut()
        authRepository.removeAuthToken()
        userRepository.removeUserStatus()
        coupleRepository.removeCoupleId()
        analyticsEventBus.emit(event = AnalyticsUserLifecycleEvent.LogOuted)
    }
}
