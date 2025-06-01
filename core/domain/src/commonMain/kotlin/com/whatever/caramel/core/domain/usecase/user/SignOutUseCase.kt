package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.UserRepository

class SignOutUseCase(
    private val userRepository: UserRepository,
    private val coupleRepository: CoupleRepository
) {
    suspend operator fun invoke() {
        userRepository.signOut()
        userRepository.deleteUserStatus()

        coupleRepository.deleteCoupleId()
    }
}