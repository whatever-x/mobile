package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.auth.SocialLoginType
import com.whatever.caramel.core.domain.entity.user.UserBasic
import com.whatever.caramel.core.domain.model.aggregate.UserAuth
import com.whatever.caramel.core.domain.model.auth.AuthToken
import com.whatever.caramel.core.domain.usecase.auth.SocialLoginInputModel
import com.whatever.caramel.core.remote.dto.auth.LoginPlatform
import com.whatever.caramel.core.remote.dto.auth.ServiceToken
import com.whatever.caramel.core.remote.dto.auth.SignInRequest
import com.whatever.caramel.core.remote.dto.auth.SignInResponse

fun SocialLoginType.toLoginPlatform(): LoginPlatform =
    when (this) {
        SocialLoginType.APPLE -> LoginPlatform.APPLE
        SocialLoginType.KAKAO -> LoginPlatform.KAKAO
    }

fun SocialLoginInputModel.toRemote() : SignInRequest {
    return SignInRequest(
        loginPlatform = this.socialLoginType.toLoginPlatform(),
        idToken = this.idToken
    )
}

fun SignInResponse.toDomain() : UserAuth {
    return UserAuth(
        userBasic = UserBasic(
            id = 0L,
            userStatus = this.userStatus.toDomain(),
            coupleId = this.coupleId
        ),
        authToken = serviceToken.toDomain()
    )
}

fun ServiceToken.toDomain() = AuthToken(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)