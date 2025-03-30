package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.validator.UserValidator

class CreateUserProfileUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        nickname: String,
        birthDay: String,
        agreementServiceTerms: Boolean,
        agreementPrivacyPolicy: Boolean
    ) {
        UserValidator.checkNicknameValidate(nickname)
            .onSuccess {
                userRepository.createUserProfile(
                    nickname = nickname,
                    birthDay = birthDay,
                    agreementServiceTerms = agreementServiceTerms,
                    agreementPrivacyPolicy = agreementPrivacyPolicy
                )
            }.onFailure {
                throw it
            }
    }
}