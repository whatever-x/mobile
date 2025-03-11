package com.whatever.caramel.core.domain.auth.model

sealed interface SocialLoginModel {

    val platform: SocialPlatform
    val idToken: String

    data class Kakao(override val idToken: String) : SocialLoginModel {
        override val platform: SocialPlatform = SocialPlatform.KAKAO
    }

    data class Apple(override val idToken: String) : SocialLoginModel {
        override val platform: SocialPlatform = SocialPlatform.APPLE
    }

}

enum class SocialPlatform {
    KAKAO,
    APPLE,
    ;
}