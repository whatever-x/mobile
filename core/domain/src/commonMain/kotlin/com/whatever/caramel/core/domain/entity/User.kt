package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.vo.user.UserAgreement
import com.whatever.caramel.core.domain.vo.user.UserProfile
import com.whatever.caramel.core.domain.vo.user.UserStatus

data class User(
    val id: Long,
    val userStatus: UserStatus,
    val userProfile: UserProfile,
    val userAgreement: UserAgreement,
)
