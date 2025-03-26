package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.entity.user.UserStatus
import com.whatever.caramel.core.domain.exception.AppExceptionCode
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository

class RefreshUserSessionUseCase (
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
){
    suspend operator fun invoke() : UserStatus {
        val oldToken = authRepository.getAuthToken()
        if(oldToken.isEmpty){
            throw CaramelException(
                code = AppExceptionCode.EMPTY_VALUE,
                message = "토큰이 만료되었습니다.",
                debugMessage = "Authentication token is empty",
            )
        }
        authRepository.refreshAuthToken(oldToken)
        return userRepository.getUserStatus()
    }
}