package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.entity.user.UserStatus
import com.whatever.caramel.core.domain.repository.UserRepository

class CheckUserStateUseCase (
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() : UserStatus {
        return userRepository.getUserStatus()
    }
}