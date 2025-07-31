package com.whatever.caramel.core.domain.entity

import kotlinx.datetime.LocalDate

/**
 * Entity - 고유한 값(id)을 통해서 해당 객체를 구분
 * 밸런스 게임을 구분 - id
 * 밸런스 게임의 옵션을 구분 - id
 * 수정/생성도 결국에는 id로 조회하거나 id로 구분되는 것
 * */
data class BalanceGame(
    val id: Long,
    val date: LocalDate,
    val question: String,
    val options: List<Option>,
) {
    /**
     * 밸런스게임 옵션은 밸런스게임이랑 강하게 연관되어 있음
     * 내부 클래스로 선언을 하게되면 확실하게 연관되어있음을 표시할 수 있음
     * 반면, 이를 사용할 때 BalanceGame.Option 사용해야함 (단독 사용 불가능)
     * */
    data class Option(
        val optionId: Long,
        val text: String,
    )
}
