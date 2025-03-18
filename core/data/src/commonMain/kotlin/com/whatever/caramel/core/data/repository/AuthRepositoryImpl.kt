package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toLoginPlatform
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.datastore.datasource.TokenDataSource
import com.whatever.caramel.core.domain.auth.model.SocialLoginModel
import com.whatever.caramel.core.domain.auth.repository.AuthRepository
import com.whatever.caramel.core.remote.datasource.RemoteAuthDataSource
import com.whatever.caramel.core.remote.dto.auth.SignInRequest

internal class AuthRepositoryImpl(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val tokenDataSource: TokenDataSource,
) : AuthRepository {

    override suspend fun loginWithSocialPlatform(socialLoginModel: SocialLoginModel) {
        when (socialLoginModel) {
            is SocialLoginModel.Kakao,
            is SocialLoginModel.Apple -> {
                val response = safeCall {
                    remoteAuthDataSource.signIn(
                        request = SignInRequest(
                            loginPlatform = socialLoginModel.platform.toLoginPlatform(),
                            idToken = socialLoginModel.idToken
                        )
                    )
                }

                tokenDataSource.createToken(
                    accessToken = response.serviceToken.accessToken,
                    refreshToken = response.serviceToken.refreshToken
                )
            }
        }
    }

}