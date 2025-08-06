package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toAuthToken
import com.whatever.caramel.core.data.mapper.toAuthResult
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.datastore.datasource.TokenDataSource
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.vo.auth.AuthResult
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.remote.datasource.RemoteAuthDataSource
import com.whatever.caramel.core.remote.dto.auth.LoginPlatformDto
import com.whatever.caramel.core.remote.dto.auth.ServiceTokenDto
import com.whatever.caramel.core.remote.dto.auth.request.SignInRequest

internal class AuthRepositoryImpl(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val localTokenDataSource: TokenDataSource,
) : AuthRepository {
    override suspend fun loginWithSocialPlatform(
        idToken: String,
        socialLoginType: SocialLoginType,
    ): AuthResult =
        safeCall {
            val request =
                SignInRequest(
                    idToken = idToken,
                    loginPlatform = LoginPlatformDto.valueOf(socialLoginType.name),
                )
            val response = remoteAuthDataSource.signIn(request = request)
            response.toAuthResult()
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

    override suspend fun setAuthToken(authToken: AuthToken) {
        safeCall {
            localTokenDataSource.createToken(
                accessToken = authToken.accessToken,
                refreshToken = authToken.refreshToken,
            )
        }
    }

    override suspend fun readAuthToken(): AuthToken =
        safeCall {
            AuthToken(
                accessToken = localTokenDataSource.fetchAccessToken(),
                refreshToken = localTokenDataSource.fetchRefreshToken(),
            )
        }

    override suspend fun removeAuthToken() {
        safeCall {
            localTokenDataSource.deleteToken()
        }
    }

    override suspend fun signOut() {
        safeCall {
            remoteAuthDataSource.deleteAccount()
        }
    }
}
