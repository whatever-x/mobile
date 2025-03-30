package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.entity.user.UserStatus
import com.whatever.caramel.core.domain.exception.code.AppExceptionCode
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository

class RefreshUserSessionUseCase (
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
){
    suspend operator fun invoke() : UserStatus {
        val localSavedToken = authRepository.getAuthToken()

        if(localSavedToken.accessToken.isEmpty() || localSavedToken.refreshToken.isEmpty()){
            throw CaramelException(
                code = AppExceptionCode.EMPTY_VALUE,
                message = "로컬에 저장된 인증 토큰이 존재하지 않습니다.",
                debugMessage = "Authentication token is empty",
            )
        }

        val newToken = authRepository.refreshAuthToken(localSavedToken)

        authRepository.saveTokens(newToken)

        return userRepository.getUserStatus()
    }
}