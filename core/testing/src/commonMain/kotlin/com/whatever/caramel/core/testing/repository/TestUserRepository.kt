package com.whatever.caramel.core.testing.repository

import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.validator.UserValidator
import com.whatever.caramel.core.domain.vo.user.Gender

import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.testing.constant.TestMessage

class TestUserRepository : UserRepository {
    var savedUserStatus: UserStatus? = null
    var createdUser: User? = null

    override suspend fun getUserStatus(): UserStatus {
        return savedUserStatus ?: throw CaramelException(
            code = NetworkErrorCode.UNKNOWN,
            message = TestMessage.UNKNOWN_ERROR
        )
    }

    override suspend fun setUserStatus(status: UserStatus) {
        savedUserStatus = status
    }

    override suspend fun createUserProfile(
        nickname: String,
        birthDay: String,
        gender: Gender,
        agreementServiceTerms: Boolean,
        agreementPrivacyPolicy: Boolean
    ): User {
        when {
            UserValidator.checkNicknameValidate(nickname).isFailure -> throw CaramelException(
                code = NetworkErrorCode.ARGS_VALIDATION_FAILED,
                message = TestMessage.ARGS_VALIDATION_ERROR
            )

            gender == Gender.IDLE -> throw CaramelException(
                code = NetworkErrorCode.INVALID_ARGUMENT,
                message = TestMessage.INVALID_ARGUMENT
            )

            !agreementPrivacyPolicy || !agreementServiceTerms -> throw CaramelException(
                code = NetworkErrorCode.INVALID_ARGUMENT,
                message = TestMessage.INVALID_ARGUMENT
            )
        }

        return createdUser ?: throw CaramelException(
            code = NetworkErrorCode.UNKNOWN,
            message = TestMessage.REQUIRE_INIT_FOR_TEST
        )
    }
}