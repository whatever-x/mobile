package com.whatever.caramel.feature.profile.edit

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditIntent
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditSideEffect
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditState
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditType
import com.whatever.caramel.feature.profile.edit.navigation.ProfileEditRoute

class ProfileEditViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProfileEditState, ProfileEditSideEffect, ProfileEditIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): ProfileEditState {
        val arguments = savedStateHandle.toRoute<ProfileEditRoute>()

        return ProfileEditState(
            editUiType = ProfileEditType.valueOf(arguments.editType)
        )
    }

    override suspend fun handleIntent(intent: ProfileEditIntent) {
        when (intent) {
            is ProfileEditIntent.ClickSaveButton -> postSideEffect(ProfileEditSideEffect.PopBackStack)
            is ProfileEditIntent.ClickCloseButton -> postSideEffect(ProfileEditSideEffect.PopBackStack)
        }
    }

}