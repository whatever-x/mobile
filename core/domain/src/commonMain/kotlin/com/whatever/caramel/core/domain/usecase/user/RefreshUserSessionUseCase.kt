package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository

class RefreshUserSessionUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): User {
        val localSavedToken = authRepository.readAuthToken()
        val newToken = authRepository.refreshAuthToken(localSavedToken)
        authRepository.setAuthToken(newToken)

        val userInfo = userRepository.getUserInfo()
        val userStatus = userInfo.userStatus
        userRepository.setUserStatus(status = userStatus)

        return userInfo
    }
}
