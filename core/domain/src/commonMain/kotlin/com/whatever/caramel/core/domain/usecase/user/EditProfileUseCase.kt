package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.validator.UserValidator

class EditProfileUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        nickname: String? = null,
        birthday: String? = null,
    ) {
        if (nickname != null) {
            UserValidator.checkNicknameValidate(nickname)
                .onFailure {
                    throw it
                }
        }
        userRepository.updateUserProfile(nickname, birthday)
    }
}
