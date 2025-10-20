package com.whatever.caramel.mvi

import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface AppIntent : UiIntent {
    data class NavigateToStartDestination(
        val userStatus: UserStatus,
    ) : AppIntent

    data object CloseErrorDialog : AppIntent

    data class ShowErrorDialog(
        val message: String,
        val description: String?,
    ) : AppIntent

    data class ShowToast(
        val message: String,
    ) : AppIntent

    data object RequestInReview : AppIntent
}
