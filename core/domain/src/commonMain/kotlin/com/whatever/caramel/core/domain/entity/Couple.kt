package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.vo.couple.CoupleStatus

data class Couple(
    val info: CoupleInfo,
    val myInfo: User,
    val partnerInfo: User,
)

data class CoupleInfo(
    val id: Long,
    val startDateMillis: Long,
    val sharedMessage: String,
    val status: CoupleStatus,
)