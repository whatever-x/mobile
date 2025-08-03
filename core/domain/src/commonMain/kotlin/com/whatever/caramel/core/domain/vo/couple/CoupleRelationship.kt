package com.whatever.caramel.core.domain.vo.couple

import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.entity.User

data class CoupleRelationship(
    val info: Couple,
    val myInfo: User,
    val partnerInfo: User?,
)
