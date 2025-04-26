package com.whatever.caramel.feature.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.feature.home.mvi.HomeState
import com.whatever.caramel.feature.home.mvi.TodoState

internal class HoeScreenPreviewProvider :
    PreviewParameterProvider<HomeState> {
    override val values: Sequence<HomeState> = sequenceOf(
        HomeState(
            daysTogether = 0,
            shareMessage = "",
            todos = emptyList(),
            isShowBottomSheet = true,
            isLoading = false,
            isSetAnniversary = false
        ),
        HomeState(
            daysTogether = 0,
            shareMessage = "",
            todos = emptyList(),
            isShowBottomSheet = false,
            isLoading = false,
            isSetAnniversary = false
        ),
        HomeState(
            daysTogether = 1120,
            shareMessage = "내일 여기서 만나",
            todos = emptyList(),
            isShowBottomSheet = false,
            isLoading = false,
            isSetAnniversary = true
        ),
        HomeState(
            daysTogether = 1120,
            shareMessage = "내일 여기서 만나",
            todos = listOf(TodoState(id = 1, title = "오늘 캘린더에 첫 번째로 적혀있는 것이 표시됩니다.")),
            isShowBottomSheet = false,
            isLoading = false,
            isSetAnniversary = true
        ),
        HomeState(
            daysTogether = 1120,
            shareMessage = "내일 여기서 만나",
            todos = (0..7).map { TodoState(it.toLong(), "오늘 캘린더에 첫 번째로 적혀있는 것이 표시됩니다.") },
            isShowBottomSheet = false,
            isLoading = false,
            isSetAnniversary = true
        ),
        HomeState(
            daysTogether = 1120,
            shareMessage = "내일 여기서 만나",
            todos = (0..7).map { TodoState(it.toLong(), "오늘 캘린더에 첫 번째로 적혀있는 것이 표시됩니다.") },
            isShowBottomSheet = false,
            isLoading = true,
            isSetAnniversary = true
        ),
    )
}