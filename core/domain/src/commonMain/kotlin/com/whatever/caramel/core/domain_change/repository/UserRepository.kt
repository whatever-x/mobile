package com.whatever.caramel.core.domain_change.repository

import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.domain.vo.user.UserStatus

interface UserRepository {
    suspend fun getUserStatus(): UserStatus

    suspend fun setUserStatus(status: UserStatus)

    suspend fun createUserProfile(
        nickname: String,
        birthDay: String,
        gender: Gender,
        agreementServiceTerms: Boolean,
        agreementPrivacyPolicy: Boolean,
    ): User

    suspend fun updateUserProfile(
        nickname: String?,
        birthday: String?,
    ): User

    suspend fun getUserInfo(): User

    suspend fun deleteUserStatus()

    suspend fun updateUserSetting(notificationEnabled: Boolean): Boolean

    suspend fun getUserSetting(): Boolean
}
