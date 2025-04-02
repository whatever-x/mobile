package com.whatever.caramel.core.domain.entity

data class Couple(
    val id: Long,
    val startDateMillis: Long,
    val sharedMessage: String,
    val myInfo : User,
    val partnerInfo: User
)