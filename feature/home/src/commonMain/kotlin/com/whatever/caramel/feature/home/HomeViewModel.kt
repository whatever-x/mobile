package com.whatever.caramel.feature.home

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.home.mvi.HomeIntent
import com.whatever.caramel.feature.home.mvi.HomeSideEffect
import com.whatever.caramel.feature.home.mvi.HomeState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay

class HomeViewModel(
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
            is HomeIntent.ClearShareMessage -> clearShareMessage()
            is HomeIntent.SaveShareMessage -> saveShareMessage()
            is HomeIntent.ShowShareMessageEditBottomSheet -> showBottomSheet()
            is HomeIntent.HideShareMessageEditBottomSheet -> hideBottomSheet()
            is HomeIntent.PullToRefresh -> refreshHomeData()
            is HomeIntent.ChangeShareMessage -> changeShareMessage(message = intent.message)
        }
    }

    private suspend fun saveShareMessage() {
        launch {
            val message = currentState.bottomSheetMessage
            // @ham2174 TODO : 기억할 말 저장 유스케이스 연결
            // 저장 성공 시
            reduce {
                copy(
                    savedShareMessage = message,
                    isShowBottomSheet = false
                )
            }
        }
    }

    private fun clearShareMessage() {
        reduce {
            copy(bottomSheetMessage = "")
        }
    }

    private fun changeShareMessage(message: String) {
        if (!message.contains("\n") && message.length <= 24) {
            reduce {
                copy(bottomSheetMessage = message)
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
            copy(
                isShowBottomSheet = false,
                bottomSheetMessage = savedShareMessage
            )
        }
    }

}