package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.repository.UserRepository

class UpdateUserSettingUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(notificationEnabled: Boolean): Boolean =
        userRepository.updateUserSetting(notificationEnabled = notificationEnabled)
}
