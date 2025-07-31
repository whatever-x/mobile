package com.whatever.caramel.core.domain_change.vo.schedule

import kotlinx.datetime.LocalDate

/**
 * 서버는 id를 제공해주고 있음, 하지만 이 id를 사용하는 곳이 없음
 * 즉, 도메인적으로 id가 의미가 없음을 뜻함
 * 하지만 Holiday는 도메인적으로 의미가 존재하기에 Vo
 * */
data class Holiday(
    val date: LocalDate,
    val name: String,
    val isHoliday: Boolean,
)
