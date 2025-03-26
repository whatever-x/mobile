package com.whatever.caramel.core.domain.model.auth

data class AuthToken(
    val accessToken : String,
    val refreshToken: String
)
