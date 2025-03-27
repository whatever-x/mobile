package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toAuthToken
import com.whatever.caramel.core.data.mapper.toLoginPlatform
import com.whatever.caramel.core.data.mapper.toUserAuthAggregation
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.datastore.datasource.TokenDataSource
import com.whatever.caramel.core.domain.model.aggregate.UserAuthAggregation
import com.whatever.caramel.core.domain.model.auth.AuthToken
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.usecase.auth.SocialLoginInputModel
import com.whatever.caramel.core.remote.datasource.RemoteAuthDataSource
import com.whatever.caramel.core.remote.dto.auth.ServiceToken
import com.whatever.caramel.core.remote.dto.auth.SignInRequest

internal class AuthRepositoryImpl(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val tokenDataSource: TokenDataSource
) : AuthRepository {

    override suspend fun loginWithSocialPlatform(inputModel: SocialLoginInputModel): UserAuthAggregation {
        return safeCall {
            val request = SignInRequest(
                idToken = inputModel.idToken,
                loginPlatform = inputModel.socialLoginType.toLoginPlatform()
            )
            val response = remoteAuthDataSource.signIn(request = request)
            response.toUserAuthAggregation()
        }
    }

    override suspend fun refreshAuthToken(oldToken: AuthToken) {
        return safeCall {
            val request = ServiceToken(
                accessToken = oldToken.accessToken,
                refreshToken = oldToken.refreshToken
            )
            remoteAuthDataSource.refresh(request)
        }
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        safeCall {
            tokenDataSource.createToken(accessToken, refreshToken)
        }
    }

    override suspend fun getAuthToken(): AuthToken {
        return safeCall {
            tokenDataSource.fetchToken().toAuthToken()
        }
    }
}