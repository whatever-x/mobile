package com.whatever.caramel.feat.profile.create

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.presentation.BaseViewModel
import com.whatever.caramel.feat.profile.create.mvi.ProfileCreateIntent
import com.whatever.caramel.feat.profile.create.mvi.ProfileCreateSideEffect
import com.whatever.caramel.feat.profile.create.mvi.ProfileCreateState

class ProfileCreateViewModel(
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