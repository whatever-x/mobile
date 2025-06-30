package com.whatever.caramel.feature.memo.mvi

import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface MemoSideEffect : UiSideEffect {
    data class NavigateToTodoDetail(val todoId: Long, val contentType: ContentType) : MemoSideEffect

    data class ShowErrorDialog(val message: String, val description: String?) : MemoSideEffect

    data class ShowErrorToast(val message: String) : MemoSideEffect
}
