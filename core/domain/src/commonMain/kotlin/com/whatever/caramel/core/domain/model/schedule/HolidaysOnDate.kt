package com.whatever.caramel.core.domain.model.schedule

import com.whatever.caramel.core.domain.vo.schedule.Holiday
import kotlinx.datetime.LocalDate

/**
 * UI 모델이지만 계층간 전달을 위해서 생성
 * Map으로 변경 가능
 * 혹은 가독성을 위해서 클래스로 관리
 * */
data class HolidaysOnDate(
    val date: LocalDate,
    val holidays: List<Holiday>,
)