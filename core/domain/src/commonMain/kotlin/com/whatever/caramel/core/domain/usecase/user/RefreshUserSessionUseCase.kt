package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.user.UserStatus

class RefreshUserSessionUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): UserStatus {
        val localSavedToken = authRepository.readAuthToken()
        val newToken = authRepository.refreshAuthToken(localSavedToken)
        authRepository.setAuthToken(newToken)

        val userInfo = userRepository.getUserInfo()
        val userStatus = userInfo.userStatus
        userRepository.setUserStatus(status = userStatus)

        return userStatus
    }
}
