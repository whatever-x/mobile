package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toAuthToken
import com.whatever.caramel.core.data.mapper.toUserAuth
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.datastore.datasource.TokenDataSource
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.domain.vo.auth.UserAuth
import com.whatever.caramel.core.remote.datasource.RemoteAuthDataSource
import com.whatever.caramel.core.remote.dto.auth.LoginPlatform
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

    override suspend fun refreshAuthToken(oldToken: AuthToken) : AuthToken {
        return safeCall {
            val request = ServiceToken(
                accessToken = oldToken.accessToken,
                refreshToken = oldToken.refreshToken
            )
            val response = remoteAuthDataSource.refresh(request)
            response.toAuthToken()
        }
    }

    override suspend fun saveTokens(authToken: AuthToken) {
        safeCall {
            tokenDataSource.createToken(
                accessToken = authToken.accessToken,
                refreshToken = authToken.refreshToken
            )
        }
    }

    override suspend fun getAuthToken(): AuthToken {
        return safeCall {
            tokenDataSource.fetchToken().toAuthToken()
        }
    }
}