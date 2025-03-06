package com.whatever.caramel.feature.memo.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface MemoIntent : UiIntent {

    data object ClickMemo : MemoIntent

}