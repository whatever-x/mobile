package com.whatever.caramel.feature.home.mvi

import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface HomeSideEffect : UiSideEffect {

    data object NavigateToSetting : HomeSideEffect

    data object NavigateToCreateContent : HomeSideEffect

    data class NavigateToContentDetail(val contentId: Long, val contentType: ContentType) : HomeSideEffect

    data object NavigateToEditAnniversary : HomeSideEffect

    data class ShowErrorDialog(val message : String, val description : String?) : HomeSideEffect

    data class ShowErrorToast(val message : String) : HomeSideEffect
}