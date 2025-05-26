package com.whatever.caramel.feature.home

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.couple.UpdateShareMessageUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.home.mvi.BalanceGameOption
import com.whatever.caramel.feature.home.mvi.HomeIntent
import com.whatever.caramel.feature.home.mvi.HomeSideEffect
import com.whatever.caramel.feature.home.mvi.HomeState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay

class HomeViewModel(
    private val updateShareMessageUseCase: UpdateShareMessageUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<HomeState, HomeSideEffect, HomeIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): HomeState {
        return HomeState()
    }

    override suspend fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ClickAnniversaryNudgeCard -> postSideEffect(HomeSideEffect.NavigateToEditAnniversary)
            is HomeIntent.ClickSettingButton -> postSideEffect(HomeSideEffect.NavigateToSetting)
            is HomeIntent.ClickTodoContent -> postSideEffect(HomeSideEffect.NavigateToContentDetail(contentId = intent.todoContentId))
            is HomeIntent.CreateTodoContent -> postSideEffect(HomeSideEffect.NavigateToCreateContent)
            is HomeIntent.SaveShareMessage -> saveShareMessage(newShareMessage = intent.newShareMessage)
            is HomeIntent.ShowShareMessageEditBottomSheet -> showBottomSheet()
            is HomeIntent.HideShareMessageEditBottomSheet -> hideBottomSheet()
            is HomeIntent.PullToRefresh -> refreshHomeData()
            is HomeIntent.ClickBalanceGameOptionButton -> selectBalanceGameOption(balanceGameOption = intent.option)
            is HomeIntent.ClickBalanceGameResultButton -> checkBalanceGameResult()
        }
    }

    private suspend fun saveShareMessage(newShareMessage: String) {
        launch {
            val updatedMessage = updateShareMessageUseCase(shareMessage = newShareMessage)

            reduce {
                copy(
                    shareMessage = updatedMessage,
                    isShowBottomSheet = false
                )
            }
        }
    }

    private suspend fun refreshHomeData() {
        launch {
            reduce { copy(isLoading = true) }

            // @ham2174 TODO : 홈 데이터 업데이트하기
            // 기억할 말 가져오기
            // 만난 날짜 가져오기
            // 오늘 일정 가져오기
            Napier.d { "데이터 업데이트" }
            delay(2000L)

            reduce { copy(isLoading = false) }
        }
    }

    private fun showBottomSheet() {
        reduce {
            copy(isShowBottomSheet = true)
        }
    }

    private fun hideBottomSheet() {
        reduce {
            copy(isShowBottomSheet = false)
        }
    }

    private fun selectBalanceGameOption(balanceGameOption: BalanceGameOption) {
        launch {
            // 밸런스 게임 옵션 선택한것을 서버에 전달
            // 서버에서 밸런스 게임 결과를 받아옴
            // 결과에 따라 BalanceGameAnswerState를 변경
                // 만일 partnerChoice가 null 이라면
                // BalanceGameAnswerState = WAITING 으로 변경
                // 만일 partnerChoice가 있다면
                // BalanceGameAnswerState = SUCCESS 로 변경

        }
    }

    private fun checkBalanceGameResult() {
        launch {
            // 밸런스 게임 결과를 서버에 요청
        }
    }

}