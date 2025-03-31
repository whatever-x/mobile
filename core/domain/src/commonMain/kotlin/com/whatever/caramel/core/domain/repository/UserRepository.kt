package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.user.UserStatus

interface UserRepository {
    suspend fun getUserStatus() : UserStatus
    suspend fun setUserStatus(status: UserStatus)
    suspend fun createUserProfile(
        nickname : String,
        birthDay : String,
        agreementServiceTerms: Boolean,
        agreementPrivacyPolicy: Boolean
    )
}