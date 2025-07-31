package com.whatever.caramel.core.domain_change.vo.balanceGame

import com.whatever.caramel.core.domain.entity.BalanceGame

/**
 * Entity의 조합이지만 밸런스 게임에 대한 결과는 도메인적 의미를 갖고 있음
 * */
data class BalanceGameResult(
    val gameInfo: BalanceGame,
    val myChoice: BalanceGame.Option?,
    val partnerChoice: BalanceGame.Option?,
)
