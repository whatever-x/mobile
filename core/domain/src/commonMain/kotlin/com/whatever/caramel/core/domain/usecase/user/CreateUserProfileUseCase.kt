package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.repository.UserRepository

data class UserProfileInputModel(
    val nickname : String,
    val birthDay : String,
    val agreementServiceTerms : Boolean,
    val agreementPrivacyPolicy: Boolean
)

class CreateUserProfileUseCase (
    private val userRepository: UserRepository
){
    suspend operator fun invoke(userProfileInputModel: UserProfileInputModel) {
        userRepository.createUserProfile(userProfileInputModel = userProfileInputModel)
    }
}