package com.whatever.caramel.feature.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.feature.home.mvi.BalanceGameOptionState
import com.whatever.caramel.feature.home.mvi.BalanceGameState
import com.whatever.caramel.feature.home.mvi.HomeState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

internal class QuizPreviewData :
    PreviewParameterProvider<HomeState> {
    override val values: Sequence<HomeState>
        get() {
            val myNickname = "내 닉네임"
            val myGender = Gender.MALE
            val partnerNickname = "상대방 닉네임"
            val partnerGender = Gender.FEMALE
            val balanceGameState = BalanceGameState(
                id = 0L,
                question = "밸런스 게임 퀴즈 질문",
                options = persistentListOf(
                    BalanceGameOptionState(id = 0, name = "옵션 1"),
                    BalanceGameOptionState(id = 1, name = "옵션 2"),
                )
            )
            val myChoiceOption = balanceGameState.options[0]
            val partnerChoiceOption = balanceGameState.options[1]

            return sequenceOf(
                HomeState( // 상대방 선택 x / 내 선택 x
                    myNickname = myNickname,
                    balanceGameCardState = HomeState.BalanceGameCardState.IDLE,
                    balanceGameState = balanceGameState,
                    partnerChoiceOption = BalanceGameOptionState(),
                    myChoiceOption = BalanceGameOptionState(),
                ),
                HomeState( // 상대방 선택 o / 내 선택 x
                    myNickname = myNickname,
                    partnerNickname = partnerNickname,
                    balanceGameCardState = HomeState.BalanceGameCardState.IDLE,
                    balanceGameState = balanceGameState,
                    partnerChoiceOption = partnerChoiceOption,
                    myChoiceOption = BalanceGameOptionState(),
                ),
                HomeState( // 상대방 선택 x / 내 선택 o
                    myNickname = myNickname,
                    partnerNickname = partnerNickname,
                    balanceGameCardState = HomeState.BalanceGameCardState.IDLE,
                    balanceGameState = balanceGameState,
                    partnerChoiceOption = BalanceGameOptionState(),
                    myChoiceOption = myChoiceOption,
                ),
                HomeState( // 상대방 선택 o / 내 선택 o
                    myNickname = myNickname,
                    partnerNickname = partnerNickname,
                    balanceGameCardState = HomeState.BalanceGameCardState.IDLE,
                    balanceGameState = balanceGameState,
                    partnerChoiceOption = partnerChoiceOption,
                    myChoiceOption = myChoiceOption,
                ),
                HomeState( // 결과 확인
                    myNickname = myNickname,
                    myGender = myGender,
                    partnerNickname = partnerNickname,
                    partnerGender = partnerGender,
                    balanceGameCardState = HomeState.BalanceGameCardState.CONFIRM,
                    balanceGameState = balanceGameState,
                    partnerChoiceOption = partnerChoiceOption,
                    myChoiceOption = myChoiceOption,
                ),
            )
        }
}