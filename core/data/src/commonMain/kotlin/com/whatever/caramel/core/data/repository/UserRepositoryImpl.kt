package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toUser
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.datastore.datasource.UserDataSource
import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.remote.datasource.RemoteUserDataSource
import com.whatever.caramel.core.remote.dto.user.request.EditUserProfileRequest
import com.whatever.caramel.core.remote.dto.user.request.UserProfileRequest
import com.whatever.caramel.core.remote.dto.user.request.UserSettingRequest

class UserRepositoryImpl(
    private val userRemoteDataSource: RemoteUserDataSource,
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getUserStatus(): UserStatus {
        return safeCall {
            val userStatus = userDataSource.getUserStatus()
            UserStatus.valueOf(value = userStatus)
        }
    }

    override suspend fun setUserStatus(status: UserStatus) {
        safeCall {
            userDataSource.setUserStatus(status.name)
        }
    }

    override suspend fun createUserProfile(
        nickname: String,
        birthDay: String,
        gender: Gender,
        agreementServiceTerms: Boolean,
        agreementPrivacyPolicy: Boolean
    ): User {
        return safeCall {
            val request = UserProfileRequest(
                nickname = nickname,
                birthday = birthDay,
                gender = gender.name,
                agreementServiceTerms = agreementServiceTerms,
                agreementPrivatePolicy = agreementPrivacyPolicy
            )
            userRemoteDataSource.createUserProfile(request).toUser()
        }
    }

    override suspend fun updateUserProfile(nickname: String?, birthday: String?): User {
        return safeCall {
            val request = EditUserProfileRequest(
                nickname = nickname,
                birthday = birthday
            )
            userRemoteDataSource.editUserProfile(request).toUser()
        }
    }

    override suspend fun getUserInfo(): User {
        return safeCall {
            userRemoteDataSource.getUserInfo().toUser()
        }
    }

    override suspend fun deleteUserStatus() {
        safeCall { userDataSource.deleteUserStatus() }
    }

    override suspend fun updateUserSetting(notificationEnabled: Boolean) : Boolean {
        val request = UserSettingRequest(notificationEnabled = notificationEnabled)
        return safeCall { userRemoteDataSource.patchUserSetting(request).notificationEnabled }
    }

    override suspend fun getUserSetting(): Boolean {
        return safeCall { userRemoteDataSource.getUserSetting().notificationEnabled }
    }
}