package com.whatever.caramel.core.domain.usecase.auth

import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.UserRepository

class LogoutUseCase (
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val coupleRepository: CoupleRepository
) {
    suspend operator fun invoke() {
        authRepository.deleteToken()
        userRepository.deleteUserStatus()
        coupleRepository.deleteCoupleId()
    }
}