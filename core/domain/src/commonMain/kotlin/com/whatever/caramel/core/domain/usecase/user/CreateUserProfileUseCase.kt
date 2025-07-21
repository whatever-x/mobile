package com.whatever.caramel.core.domain.usecase.user

import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.validator.UserValidator
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.domain.vo.user.UserStatus

class CreateUserProfileUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        nickname: String,
        birthDay: String,
        gender: Gender,
        agreementServiceTerms: Boolean,
        agreementPrivacyPolicy: Boolean,
    ): UserStatus {
        val validatedNickname = UserValidator.checkNicknameValidate(nickname).getOrThrow()

        val createProfileResult =
            userRepository.createUserProfile(
                nickname = validatedNickname,
                birthDay = birthDay,
                gender = gender,
                agreementServiceTerms = agreementServiceTerms,
                agreementPrivacyPolicy = agreementPrivacyPolicy,
            )
        val userStatus = createProfileResult.userStatus
        userRepository.setUserStatus(status = userStatus)

        return userStatus
    }
}
