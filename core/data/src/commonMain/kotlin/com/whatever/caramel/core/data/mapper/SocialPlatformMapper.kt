package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.auth.model.SocialPlatform
import com.whatever.caramel.core.remote.dto.auth.LoginPlatform

fun SocialPlatform.toLoginPlatform(): LoginPlatform =
    when (this) {
        SocialPlatform.APPLE -> LoginPlatform.APPLE
        SocialPlatform.KAKAO -> LoginPlatform.KAKAO
    }