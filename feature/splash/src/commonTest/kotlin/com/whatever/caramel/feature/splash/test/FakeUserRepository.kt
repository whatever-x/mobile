package com.whatever.caramel.feature.splash.test

import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.testing.util.TestMessage

class FakeUserRepository : UserRepository {
    var savedUserStatus: UserStatus? = null

    override suspend fun getUserStatus(): UserStatus {
        return savedUserStatus ?: throw CaramelException(
            code = NetworkErrorCode.UNKNOWN,
            message = TestMessage.REQUIRE_INIT_FOR_TEST
        )
    }

    override suspend fun setUserStatus(status: UserStatus) {
        savedUserStatus = status
    }

    override suspend fun createUserProfile(
        nickname: String,
        birthDay: String,
        agreementServiceTerms: Boolean,
        agreementPrivacyPolicy: Boolean
    ): User {
        throw UnsupportedOperationException(TestMessage.NOT_USE_IN_TEST)
    }
}