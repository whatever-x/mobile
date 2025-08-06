package com.whatever.caramel.core.data.interceptor

import com.whatever.caramel.core.datastore.datasource.LocalTokenDataSource
import com.whatever.caramel.core.remote.datasource.RemoteAuthDataSource
import com.whatever.caramel.core.remote.dto.auth.ServiceTokenDto
import com.whatever.caramel.core.remote.network.interceptor.TokenInterceptor

class TokenInterceptorImpl(
    private val localTokenDataSource: LocalTokenDataSource,
    private val authDataSource: RemoteAuthDataSource,
) : TokenInterceptor {
    override suspend fun getAccessToken(): String = localTokenDataSource.fetchAccessToken()

    override suspend fun getRefreshToken(): String = localTokenDataSource.fetchRefreshToken()

    /**
     * 401 에러 발생시 데이터스토어에 저장된 리프레쉬 토큰을 가져와 refresh API를 호출합니다.
     * 가져온 리프레쉬 토큰이 Null이거나 예외가 발생할 경우 false를 반환합니다.
     * refresh API 호출이 성공했을 경우 데이터 스토어에 반환된 토큰을 저장합니다.
     * @author ham2174
     * @since 2025.03.15
     */
    override suspend fun refresh(): Boolean {
        try {
            val accessToken = localTokenDataSource.fetchAccessToken()
            val refreshToken = localTokenDataSource.fetchRefreshToken()

            if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
                val response =
                    authDataSource.refresh(
                        request =
                            ServiceTokenDto(
                                accessToken = accessToken,
                                refreshToken = refreshToken,
                            ),
                    )

                localTokenDataSource.saveToken(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                )

                return true
            } else {
                // @ham2174 TODO : 로그아웃 API 호출
                return false
            }
        } catch (e: Exception) {
            // @ham2174 TODO : 로그아웃 API 호출
            return false
        }
    }
}
