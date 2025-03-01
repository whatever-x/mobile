package com.whatever.caramel.feat.main.memo.mvi

import com.whatever.caramel.core.presentation.UiIntent

sealed interface MemoIntent : UiIntent {

    data object ClickMemo : MemoIntent

}