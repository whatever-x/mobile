package com.whatever.caramel.feature.profile.edit.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class ProfileEditState(
    val editUiType: ProfileEditType = ProfileEditType.NICK_NAME,
    val nickName: String = "",
    val birthday: String = "",
    val dDay: String = ""
) : UiState

enum class ProfileEditType {
    NICK_NAME,
    BIRTHDAY,
    D_DAY,
    ;
}