package com.whatever.caramel.core.domain.vo.auth

data class AuthToken(
    val accessToken : String,
    val refreshToken: String
)
