package com.whatever.caramel.core.domain.usecase.auth

import com.whatever.caramel.core.domain.repository.AuthRepository

class LogoutUseCase (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        // TODO : 서버 API 개발 후 추가 구현
        authRepository.deleteToken()
    }
}