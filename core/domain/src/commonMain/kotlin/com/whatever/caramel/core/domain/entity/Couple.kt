package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.vo.couple.CoupleStatus

data class Couple(
    val id: Long,
    val startDate: String,
    val sharedMessage: String,
    val status: CoupleStatus,
)