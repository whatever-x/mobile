package com.whatever.caramel.feat.profile.presentation.edit.mvi

import com.whatever.caramel.core.presentation.UiState

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