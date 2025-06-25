package com.whatever.caramel.core.testing.constants

import com.whatever.caramel.core.domain.vo.user.Gender

object TestUserInfo {
    const val TEST_USER_ID = 123L

    const val TEST_USER_NICKNAME = "testUser"
    const val INVALID_LENGTH_NICKNAME = "testUserUser"
    const val INVALID_PATTERN_NICKNAME = "test_use"

    const val TEST_BIRTH_DAY = "2025.04.03"

    val TEST_USER_GENDER = Gender.MALE
}