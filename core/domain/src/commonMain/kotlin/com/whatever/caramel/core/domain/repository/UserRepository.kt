package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.user.UserStatus
import com.whatever.caramel.core.domain.usecase.user.UserProfileInputModel

interface UserRepository {
    suspend fun getUserStatus() : UserStatus
    suspend fun setUserStatus(status: UserStatus)
    suspend fun createUserProfile(userProfileInputModel: UserProfileInputModel)
}