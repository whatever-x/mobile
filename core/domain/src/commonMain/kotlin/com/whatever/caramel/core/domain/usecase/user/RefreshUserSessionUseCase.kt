package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.user.UserStatus

class RefreshUserSessionUseCase (
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
){
    suspend operator fun invoke() : UserStatus {
        val localSavedToken = authRepository.getAuthToken()
        val newToken = authRepository.refreshAuthToken(localSavedToken)
        authRepository.saveTokens(newToken)

        return userRepository.getUserStatus()
    }
}