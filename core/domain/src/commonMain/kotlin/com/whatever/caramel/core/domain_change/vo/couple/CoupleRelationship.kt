package com.whatever.caramel.core.domain_change.vo.couple

import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.entity.User

/**
 * 해당 클래스를 사용하는 유즈케이스를 직접 사용하지 않고 Repository에서 그냥 호출함
 * 유즈케이스를 사용하면 도메인적으로 의미가 생기기에 VO
 * */
data class CoupleRelationship(
    val info: Couple,
    val myInfo: User,
    val partnerInfo: User,
)