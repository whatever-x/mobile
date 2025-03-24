package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.repository.UserRepository

class SetOnboardingCompletionUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(completion: Boolean) {
        userRepository.setOnboardingCompleted(completion)
    }
}