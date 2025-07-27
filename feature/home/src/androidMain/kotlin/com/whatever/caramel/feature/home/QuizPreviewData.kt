package com.whatever.caramel.feature.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.feature.home.mvi.BalanceGameCard
import com.whatever.caramel.feature.home.mvi.BalanceGameOptionItem
import com.whatever.caramel.feature.home.mvi.HomeState
import kotlinx.collections.immutable.toImmutableList

internal class QuizPreviewData : PreviewParameterProvider<HomeState> {
    override val values: Sequence<HomeState>
        get() {
            val myNickname = "내 닉네임"
            val partnerNickname = "상대방 닉네임"
            val balanceGameOptions =
                listOf(
                    BalanceGameOptionItem(id = 0, name = "옵션 1"),
                    BalanceGameOptionItem(id = 1, name = "옵션 2"),
                ).toImmutableList()
            val balanceGameCard =
                BalanceGameCard.initState().copy(
                    question = "옵션1과 2중에 뭐가더 좋나요?",
                    options = balanceGameOptions,
                )

            return sequenceOf(
                // 상대방 선택 x / 내 선택 x
                HomeState(
                    myNickname = myNickname,
                    balanceGameCard =
                        balanceGameCard.copy(
                            myOption = null,
                            partnerOption = null,
                        ),
                ),
                // 상대방 선택 o / 내 선택 x
                HomeState(
                    myNickname = myNickname,
                    partnerNickname = partnerNickname,
                    balanceGameCard =
                        balanceGameCard.copy(
                            myOption = null,
                            partnerOption = balanceGameOptions[0],
                        ),
                ),
                // 상대방 선택 x / 내 선택 o
                HomeState(
                    myNickname = myNickname,
                    partnerNickname = partnerNickname,
                    balanceGameCard =
                        balanceGameCard.copy(
                            myOption = balanceGameOptions[0],
                            partnerOption = null,
                        ),
                ),
                // 상대방 선택 o / 내 선택 o
                HomeState(
                    myNickname = myNickname,
                    partnerNickname = partnerNickname,
                    balanceGameCard =
                        balanceGameCard.copy(
                            myOption = balanceGameOptions[0],
                            partnerOption = balanceGameOptions[1],
                        ),
                ),
                // 결과 확인
                HomeState(
                    myNickname = myNickname,
                    myGender = Gender.MALE,
                    partnerNickname = partnerNickname,
                    partnerGender = Gender.FEMALE,
                    isBalanceGameCardRotated = true,
                    balanceGameCard =
                        balanceGameCard.copy(
                            myOption = balanceGameOptions[0],
                            partnerOption = balanceGameOptions[0],
                        ),
                ),
            )
        }
}
