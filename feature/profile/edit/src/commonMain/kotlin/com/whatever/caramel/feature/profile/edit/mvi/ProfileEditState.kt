package com.whatever.caramel.feature.profile.edit.mvi

import com.whatever.caramel.core.ui.picker.DateUiState
import com.whatever.caramel.core.viewmodel.UiState

data class ProfileEditState(
    val editUiType: ProfileEditType = ProfileEditType.NONE,
    val nickName: String = "",
    val birthDay : DateUiState = DateUiState.currentDate(),
    val startDate: DateUiState = DateUiState.currentDate()
) : UiState {
    val isSaveButtonEnabled : Boolean
        get() = when(editUiType) {
            ProfileEditType.NICKNAME -> nickName.isNotEmpty()
            ProfileEditType.BIRTHDAY -> true
            ProfileEditType.START_DATE -> true
            else -> false
        }
}

enum class ProfileEditType {
    NONE,
    NICKNAME,
    BIRTHDAY,
    START_DATE,
    ;
}