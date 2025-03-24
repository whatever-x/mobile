package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toDomain
import com.whatever.caramel.core.data.mapper.toRemote
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.datastore.datasource.TokenDataSource
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.model.aggregate.UserAuthAggregation
import com.whatever.caramel.core.domain.model.auth.AuthToken
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.usecase.auth.SocialLoginInputModel
import com.whatever.caramel.core.remote.datasource.RemoteAuthDataSource
import com.whatever.caramel.core.remote.dto.auth.ServiceToken

internal class AuthRepositoryImpl(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val tokenDataSource: TokenDataSource
) : AuthRepository {

    override suspend fun loginWithSocialPlatform(inputModel: SocialLoginInputModel): UserAuthAggregation {
        return safeCall {
            val request = inputModel.toRemote()
            val response = remoteAuthDataSource.signIn(request = request)
            response.toDomain()
        }
    }

    override suspend fun refreshAuthToken() : AuthToken{
        return safeCall {
            val savedServiceToken = tokenDataSource.fetchToken()
            if (savedServiceToken.first == null || savedServiceToken.second == null) {
                throw CaramelException(
                    message = "Token Not Found",
                    debugMessage = null,
                    errorUiType = ErrorUiType.EMPTY_UI
                )
            }
            val request = ServiceToken(
                accessToken = savedServiceToken.first!!,
                refreshToken = savedServiceToken.second!!
            )
            remoteAuthDataSource.refresh(request).toDomain()
        }
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        safeCall {
            tokenDataSource.createToken(accessToken, refreshToken)
        }
    }
}