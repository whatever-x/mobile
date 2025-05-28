package com.whatever.caramel.feature.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.feature.home.mvi.BalanceGameOption
import com.whatever.caramel.feature.home.mvi.HomeState
import kotlinx.collections.immutable.toImmutableList

internal class QuizPreviewData :
    PreviewParameterProvider<HomeState> {
    override val values: Sequence<HomeState>
        get() {
            val myNickname = "내 닉네임"
            val partnerNickname = "상대방 닉네임"
            val balanceGameOptions = listOf(
                BalanceGameOption(id = 0, name = "옵션 1"),
                BalanceGameOption(id = 1, name = "옵션 2"),
            )
            val myChoiceOption = balanceGameOptions[0]
            val partnerChoiceOption = balanceGameOptions[1]

            return sequenceOf(
                HomeState( // 상대방 선택 x / 내 선택 x
                    myNickname = myNickname,
                    balanceGameCardState = HomeState.BalanceGameCardState.IDLE,
                    balanceGameQuestion = "밸런스 게임 퀴즈 질문",
                    balanceGameOptions = balanceGameOptions.toImmutableList(),
                    balanceGameAnswerState = HomeState.BalanceGameAnswerState.IDLE,
                ),
                HomeState( // 상대방 선택 o / 내 선택 x
                    myNickname = myNickname,
                    partnerNickname = partnerNickname,
                    balanceGameCardState = HomeState.BalanceGameCardState.IDLE,
                    balanceGameQuestion = "밸런스 게임 퀴즈 질문",
                    balanceGameOptions = balanceGameOptions.toImmutableList(),
                    balanceGameAnswerState = HomeState.BalanceGameAnswerState.IDLE,
                    partnerChoice = partnerChoiceOption
                ),
                HomeState( // 상대방 선택 x / 내 선택 o
                    myNickname = myNickname,
                    partnerNickname = partnerNickname,
                    balanceGameCardState = HomeState.BalanceGameCardState.IDLE,
                    balanceGameQuestion = "밸런스 게임 퀴즈 질문",
                    balanceGameOptions = balanceGameOptions.toImmutableList(),
                    balanceGameAnswerState = HomeState.BalanceGameAnswerState.WAITING,
                    myChoiceOption = myChoiceOption
                ),
                HomeState( // 상대방 선택 o / 내 선택 o
                    myNickname = myNickname,
                    partnerNickname = partnerNickname,
                    balanceGameCardState = HomeState.BalanceGameCardState.IDLE,
                    balanceGameQuestion = "밸런스 게임 퀴즈 질문",
                    balanceGameOptions = balanceGameOptions.toImmutableList(),
                    balanceGameAnswerState = HomeState.BalanceGameAnswerState.CHECK_RESULT,
                    myChoiceOption = myChoiceOption,
                    partnerChoice = partnerChoiceOption
                ),
                HomeState( // 결과 확인
                    myNickname = myNickname,
                    partnerNickname = partnerNickname,
                    balanceGameCardState = HomeState.BalanceGameCardState.CONFIRM,
                    balanceGameQuestion = "밸런스 게임 퀴즈 질문",
                    balanceGameOptions = balanceGameOptions.toImmutableList(),
                    balanceGameAnswerState = HomeState.BalanceGameAnswerState.CHECK_RESULT,
                    myChoiceOption = myChoiceOption,
                    partnerChoice = partnerChoiceOption
                ),
            )
        }
}