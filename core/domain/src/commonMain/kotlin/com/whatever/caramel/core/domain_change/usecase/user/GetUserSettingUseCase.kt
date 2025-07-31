package com.whatever.caramel.core.domain_change.usecase.user

import com.whatever.caramel.core.domain.repository.UserRepository

class GetUserSettingUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Boolean = userRepository.getUserSetting()
}
