package com.whatever.caramel.feature.profile.create

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.user.CreateUserProfileUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateIntent
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateSideEffect
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateState

class ProfileCreateViewModel(
    private val createUserProfileUseCase: CreateUserProfileUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProfileCreateState, ProfileCreateSideEffect, ProfileCreateIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): ProfileCreateState {
        return ProfileCreateState()
    }

    override suspend fun handleIntent(intent: ProfileCreateIntent) {
        when (intent) {
            is ProfileCreateIntent.ClickCreateButton -> postSideEffect(ProfileCreateSideEffect.NavigateToConnectCouple)
            is ProfileCreateIntent.ClickBackButton -> postSideEffect(ProfileCreateSideEffect.NavigateToLogin)
        }
    }

}