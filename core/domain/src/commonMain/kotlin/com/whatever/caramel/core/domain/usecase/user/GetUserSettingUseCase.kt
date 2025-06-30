package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.repository.UserRepository

class GetUserSettingUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Boolean {
        return userRepository.getUserSetting()
    }
}
