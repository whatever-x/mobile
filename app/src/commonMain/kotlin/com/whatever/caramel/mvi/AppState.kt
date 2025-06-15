package com.whatever.caramel.mvi

import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.viewmodel.UiState

data class AppState(
    val userStatus: UserStatus = UserStatus.NONE,
    val isShowErrorDialog: Boolean = false,
    val dialogMessage: String = "",
    val dialogDescription : String = "",
): UiState
