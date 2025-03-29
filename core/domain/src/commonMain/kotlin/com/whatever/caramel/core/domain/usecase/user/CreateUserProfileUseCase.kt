package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.validator.UserValidator

data class UserProfileInputModel(
    val nickname: String,
    val birthDay: String,
    val agreementServiceTerms: Boolean,
    val agreementPrivacyPolicy: Boolean
)

class CreateUserProfileUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userProfileInputModel: UserProfileInputModel) {
        UserValidator.checkNicknameValidate(userProfileInputModel.nickname)
            .onSuccess {
                userRepository.createUserProfile(userProfileInputModel = userProfileInputModel)
            }.onFailure {
                throw it
            }
    }
}