package com.whatever.caramel.core.domain.entity

data class Couple(
    val info: CoupleInfo,
    val myInfo: User,
    val partnerInfo: User
)

data class CoupleInfo(
    val id: Long,
    val startDateMillis: Long,
    val sharedMessage: String,
)