package com.whatever.caramel.core.domain_change.vo.auth

/**
 * VO(Value Object)는 도메인적으로 가치가 있는 모델들
 * 동등성의 기준이 전체 프로퍼티를 기준으로 가져감
 * */
data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
)
