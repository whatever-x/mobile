package com.whatever.caramel.feature.home.mvi

import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface HomeSideEffect : UiSideEffect {
    data object NavigateToSetting : HomeSideEffect

    data object NavigateToCreateContent : HomeSideEffect

    data class NavigateToContentDetail(
        val contentId: Long,
        val contentType: ContentType,
    ) : HomeSideEffect

    data object NavigateToEditAnniversary : HomeSideEffect

    data object NavigateToBalanceGameHistory : HomeSideEffect

    data class NavigateToBalanceGameShare(
        val gameId: Long,
        val question: String,
        val myNickname: String,
        val myGender: String,
        val myChoice: String,
        val partnerNickname: String,
        val partnerGender: String,
        val partnerChoice: String,
    ) : HomeSideEffect

    data class ShowErrorDialog(
        val message: String,
        val description: String?,
    ) : HomeSideEffect

    data class ShowErrorToast(
        val message: String,
    ) : HomeSideEffect

    data object HideKeyboard : HomeSideEffect
}
