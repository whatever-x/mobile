package com.whatever.caramel.core.domain_change.usecase.user

import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.user.UserStatus

class GetUserStatusUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): UserStatus {
        val userStatus = userRepository.getUserStatus()

        return userStatus
    }
}
