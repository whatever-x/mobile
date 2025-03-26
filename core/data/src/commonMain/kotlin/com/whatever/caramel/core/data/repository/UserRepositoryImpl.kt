package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toUserStatus
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.datastore.datasource.UserDataSource
import com.whatever.caramel.core.domain.entity.user.UserStatus
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.usecase.user.UserProfileInputModel
import com.whatever.caramel.core.remote.datasource.RemoteUserDataSource
import com.whatever.caramel.core.remote.dto.user.UserProfileRequest

class UserRepositoryImpl(
    private val userRemoteDataSource : RemoteUserDataSource,
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getOnboardingCompleted(): Boolean {
        return safeCall {
            userDataSource.getOnBoardingCompletion()
        }
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        safeCall {
            userDataSource.setOnBoardingCompletion(completed)
        }
    }

    override suspend fun getUserStatus(): UserStatus {
        return safeCall {
            userDataSource.getUserStatus().toUserStatus()
        }
    }

    override suspend fun setUserStatus(status: UserStatus) {
        safeCall {
            userDataSource.setUserStatus(status.name)
        }
    }

    override suspend fun createUserProfile(userProfileInputModel: UserProfileInputModel) {
        safeCall {
            val request = UserProfileRequest(
                nickname = userProfileInputModel.nickname.toString(),
                birthday = userProfileInputModel.birthDay,
                agreementServiceTerms = userProfileInputModel.agreementServiceTerms,
                agreementPrivatePolicy = userProfileInputModel.agreementPrivacyPolicy
            )
            userRemoteDataSource.createUserProfile(request)
        }
    }
}