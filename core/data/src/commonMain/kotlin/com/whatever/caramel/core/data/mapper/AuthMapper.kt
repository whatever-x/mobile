package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.auth.SocialLoginType
import com.whatever.caramel.core.domain.entity.user.UserBasic
import com.whatever.caramel.core.domain.model.aggregate.UserAuthAggregation
import com.whatever.caramel.core.domain.model.auth.AuthToken
import com.whatever.caramel.core.remote.dto.auth.LoginPlatform
import com.whatever.caramel.core.remote.dto.auth.ServiceToken
import com.whatever.caramel.core.remote.dto.auth.SignInResponse

fun SocialLoginType.toLoginPlatform(): LoginPlatform =
    when (this) {
        SocialLoginType.APPLE -> LoginPlatform.APPLE
        SocialLoginType.KAKAO -> LoginPlatform.KAKAO
    }


fun SignInResponse.toUserAuthAggregation() : UserAuthAggregation {
    return UserAuthAggregation(
        userBasic = UserBasic(
            id = 0L,
            userStatus = this.userStatus.toUserStatus(),
            coupleId = this.coupleId
        ),
        authToken = serviceToken.toAuthToken()
    )
}

fun ServiceToken.toAuthToken() = AuthToken(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)

fun Pair<String, String>.toAuthToken() : AuthToken {
    return AuthToken(
        accessToken = this.first,
        refreshToken = this.second
    )
}