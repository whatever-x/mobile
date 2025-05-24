package com.whatever.caramel.feature.home

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.calendar.GetTodayScheduleUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleInfoUseCase
import com.whatever.caramel.core.domain.usecase.couple.UpdateShareMessageUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.home.mvi.HomeIntent
import com.whatever.caramel.feature.home.mvi.HomeSideEffect
import com.whatever.caramel.feature.home.mvi.HomeState
import com.whatever.caramel.feature.home.mvi.TodoState

class HomeViewModel(
    private val updateShareMessageUseCase: UpdateShareMessageUseCase,
    private val getCoupleInfoUseCase: GetCoupleInfoUseCase,
    private val getTodayScheduleUseCase: GetTodayScheduleUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<HomeState, HomeSideEffect, HomeIntent>(savedStateHandle) {

    init {
        launch {
            initCoupleInfo()
            initSchedules()
        }
    }

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
            initCoupleInfo()
            initSchedules()
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

    private suspend fun initCoupleInfo() {
        val coupleInfo = getCoupleInfoUseCase()

        reduce {
            copy(
                daysTogether = coupleInfo.daysTogether,
                shareMessage = coupleInfo.sharedMessage,
            )
        }
    }

    private suspend fun initSchedules() {
        val schedules = getTodayScheduleUseCase()

        if (schedules.isNotEmpty()) {
            val todoUiState = schedules.map { todo ->
                TodoState(
                    id = todo.id,
                    title = todo.title,
                )
            }

            reduce {
                copy(
                    todos = todoUiState
                )
            }
        }
    }

}