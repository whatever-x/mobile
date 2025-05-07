package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.user.UserStatus

class RefreshUserSessionUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {
        val localSavedToken = authRepository.getAuthToken()
        val newToken = authRepository.refreshAuthToken(localSavedToken)
        authRepository.saveTokens(newToken)
    }
}