package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.vo.couple.CoupleStatus

data class Couple(
    val id: Long,
    val startDateMillis: Long,
    val sharedMessage: String,
    val status: CoupleStatus,
)