package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toAuthToken
import com.whatever.caramel.core.data.mapper.toUserAuth
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.datastore.datasource.TokenDataSource
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.domain.model.user.UserAuth
import com.whatever.caramel.core.remote.datasource.RemoteAuthDataSource
import com.whatever.caramel.core.remote.dto.auth.LoginPlatformDto
import com.whatever.caramel.core.remote.dto.auth.ServiceTokenDto
import com.whatever.caramel.core.remote.dto.auth.request.SignInRequest

internal class AuthRepositoryImpl(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val tokenDataSource: TokenDataSource,
) : AuthRepository {
    override suspend fun loginWithSocialPlatform(
        idToken: String,
        socialLoginType: SocialLoginType,
    ): UserAuth =
        safeCall {
            val request =
                SignInRequest(
                    idToken = idToken,
                    loginPlatform = LoginPlatformDto.valueOf(socialLoginType.name),
                )
            val response = remoteAuthDataSource.signIn(request = request)
            response.toUserAuth()
        }

    override suspend fun refreshAuthToken(oldToken: AuthToken): AuthToken =
        safeCall {
            val request =
                ServiceTokenDto(
                    accessToken = oldToken.accessToken,
                    refreshToken = oldToken.refreshToken,
                )
            val response = remoteAuthDataSource.refresh(request)
            response.toAuthToken()
        }

    override suspend fun saveTokens(authToken: AuthToken) {
        safeCall {
            tokenDataSource.createToken(
                accessToken = authToken.accessToken,
                refreshToken = authToken.refreshToken,
            )
        }
    }

    override suspend fun getAuthToken(): AuthToken =
        safeCall {
            AuthToken(
                accessToken = tokenDataSource.fetchAccessToken(),
                refreshToken = tokenDataSource.fetchRefreshToken(),
            )
        }

    override suspend fun deleteToken() {
        safeCall {
            tokenDataSource.deleteToken()
        }
    }

    override suspend fun signOut() {
        safeCall {
            remoteAuthDataSource.signOut()
        }
    }
}
