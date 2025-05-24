package com.whatever.caramel.feature.memo.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface MemoIntent : UiIntent {

    data class ClickMemo(val memoId : Long) : MemoIntent

}