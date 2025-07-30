package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.model.user.UserAuth
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.remote.dto.auth.ServiceTokenDto
import com.whatever.caramel.core.remote.dto.auth.response.SignInResponse

fun SignInResponse.toUserAuth(): UserAuth =
    UserAuth(
        coupleId = this.coupleId,
        nickname = this.nickname,
        userStatus = UserStatus.valueOf(this.userStatus.name),
        birthday = this.birthDay ?: "",
        authToken = serviceToken.toAuthToken(),
    )

fun ServiceTokenDto.toAuthToken() =
    AuthToken(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
    )
