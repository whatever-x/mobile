package com.whatever.caramel.core.domain_change.vo.user

import com.whatever.caramel.core.domain.vo.auth.AuthToken

/**
 * 계층간 전달을 위해서 사용되는 모델 같지만 비즈니스 로직에서 사용중
 * 도메인에서 의미가 있는 클래스기에 VO
 * */
data class UserAuth(
    val coupleId: Long? = null,
    val userStatus: UserStatus,
    val nickname: String? = null,
    val birthday: String? = null,
    val authToken: AuthToken,
)