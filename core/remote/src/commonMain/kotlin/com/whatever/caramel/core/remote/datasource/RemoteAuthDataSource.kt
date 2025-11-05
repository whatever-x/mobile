package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.auth.ServiceTokenDto
import com.whatever.caramel.core.remote.dto.auth.request.SignInRequest
import com.whatever.caramel.core.remote.dto.auth.response.SignInResponse
import com.whatever.caramel.core.remote.dto.user.response.UserSessionRefreshResponse

interface RemoteAuthDataSource {
    @Deprecated("userId 필드 추가 됨에 따라 `signInV2` API로 대체 되었습니다.")
    suspend fun signIn(request: SignInRequest): SignInResponse

    suspend fun logOut()

    /**
     * 인터셉터의 토큰을 리프레쉬 할 때 사용
     * @author ham2174
     */
    suspend fun refresh(request: ServiceTokenDto): ServiceTokenDto

    suspend fun deleteAccount()

    suspend fun signInV2(request: SignInRequest): SignInResponse

    /**
     * 사용자 세션을 리프레쉬 할 때 사용
     * @author ham2174
     */
    suspend fun refreshV2(request: ServiceTokenDto): UserSessionRefreshResponse
}
