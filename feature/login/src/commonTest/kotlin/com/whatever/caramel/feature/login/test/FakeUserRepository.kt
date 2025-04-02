package com.whatever.caramel.feature.login.test

import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.testing.util.TestMessage

class FakeUserRepository : UserRepository {
    private var userStatus = UserStatus.NONE

    override suspend fun getUserStatus(): UserStatus {
        return userStatus
    }

    override suspend fun setUserStatus(status: UserStatus) {
        userStatus = status
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