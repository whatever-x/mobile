package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.UserAuth
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.remote.dto.auth.ServiceTokenDto
import com.whatever.caramel.core.remote.dto.auth.response.SignInResponse
import com.whatever.caramel.core.util.DateParser.toMillisecond

fun SignInResponse.toUserAuth(): UserAuth {
    return UserAuth(
        coupleId = this.coupleId,
        nickname = this.nickname,
        userStatus = UserStatus.valueOf(this.userStatus.name),
        birthDayMillisecond = this.birthDay?.toMillisecond(),
        authToken = serviceToken.toAuthToken()
    )
}

fun ServiceTokenDto.toAuthToken() = AuthToken(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)