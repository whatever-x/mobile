package com.whatever.caramel.core.domain.policy

/**
 * 도메인 성격을 가진 정책
 * 기존에 yearSize와 YEAR_RANGE는 UI에서 사용되는 정책임
 * */
data object CalendarPolicy {
    const val MIN_YEAR = 1900
    const val MAX_YEAR = 2100
}