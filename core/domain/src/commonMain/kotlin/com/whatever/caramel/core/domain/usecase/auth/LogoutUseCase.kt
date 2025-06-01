package com.whatever.caramel.core.domain.usecase.auth

import com.whatever.caramel.core.domain.repository.AuthRepository

class LogoutUseCase (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.deleteToken()
    }
}