package com.whatever.caramel.core.testing.factory

import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.vo.user.UserMetaData
import com.whatever.caramel.core.domain.vo.user.UserProfile
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.testing.constants.TestUserInfo

object UserTestFactory {
    fun createNewUser() =
        User(
            id = TestUserInfo.TEST_USER_ID,
            userStatus = UserStatus.SINGLE,
            userProfile =
                UserProfile(
                    nickName = TestUserInfo.TEST_USER_NICKNAME,
                    birthday = TestUserInfo.TEST_BIRTH_DAY,
                    gender = TestUserInfo.TEST_USER_GENDER,
                ),
            userMetaData =
                UserMetaData(
                    agreementServiceTerms = true,
                    agreementPrivacyPolicy = true,
                ),
        )
}
