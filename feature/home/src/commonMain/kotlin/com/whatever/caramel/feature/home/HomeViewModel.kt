package com.whatever.caramel.feature.home

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.home.mvi.HomeIntent
import com.whatever.caramel.feature.home.mvi.HomeSideEffect
import com.whatever.caramel.feature.home.mvi.HomeState

class HomeViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<HomeState, HomeSideEffect, HomeIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): HomeState {
        return HomeState()
    }

    override suspend fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ClickTodoItem -> postSideEffect(HomeSideEffect.NavigateToTodoDetail)
            is HomeIntent.ClickCreateTodoItem -> postSideEffect(HomeSideEffect.NavigateToCreateTodo)
            is HomeIntent.ClickStartedCoupleDayButton -> postSideEffect(HomeSideEffect.NavigateToStartedCoupleDay)
            is HomeIntent.ClickSettingButton -> postSideEffect(HomeSideEffect.NavigateToSetting)
        }
    }

}