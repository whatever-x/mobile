package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toLoginPlatform
import com.whatever.caramel.core.domain.auth.model.SocialLoginModel
import com.whatever.caramel.core.domain.auth.repository.AuthRepository
import com.whatever.caramel.core.remote.datasource.RemoteAuthDataSource
import com.whatever.caramel.core.remote.dto.auth.SignInRequest
import io.github.aakira.napier.Napier

internal class AuthRepositoryImpl(
    private val remoteAuthDataSource: RemoteAuthDataSource
) : AuthRepository {

    override suspend fun loginWithSocialPlatform(socialLoginModel: SocialLoginModel) {
        when (socialLoginModel) {
            is SocialLoginModel.Kakao,
            is SocialLoginModel.Apple-> {
                val result = remoteAuthDataSource.signIn(
                    request = SignInRequest(
                        loginPlatform = socialLoginModel.platform.toLoginPlatform(),
                        idToken = socialLoginModel.idToken
                    )
                )

                Napier.d { "결과 \n$result" }
            }
        }
    }

}