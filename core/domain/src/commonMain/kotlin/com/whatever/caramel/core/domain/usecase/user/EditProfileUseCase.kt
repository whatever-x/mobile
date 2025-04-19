package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.validator.UserValidator

class EditProfileUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        nickname: String,
        birthday: String
    ) {
        UserValidator.checkNicknameValidate(nickname)
            .onSuccess {
                userRepository.updateUserProfile(
                    nickname = nickname,
                    birthday = birthday
                )
            }
            .onFailure {
                throw it
            }
    }
}