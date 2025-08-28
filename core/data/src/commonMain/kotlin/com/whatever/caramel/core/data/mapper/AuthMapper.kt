package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.vo.auth.AuthResult
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.remote.dto.auth.ServiceTokenDto
import com.whatever.caramel.core.remote.dto.auth.response.SignInResponse

internal fun SignInResponse.toAuthResult(): AuthResult =
    AuthResult(
        coupleId = this.coupleId,
        userStatus = UserStatus.valueOf(this.userStatus.name),
        authToken = serviceToken.toAuthToken(),
    )

internal fun ServiceTokenDto.toAuthToken() =
    AuthToken(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
    )
