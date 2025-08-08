package com.whatever.caramel.feature.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.feature.home.mvi.HomeState
import com.whatever.caramel.feature.home.mvi.ScheduleItem
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

internal class HomeScreenPreviewData : PreviewParameterProvider<HomeState> {
    override val values: Sequence<HomeState> =
        sequenceOf(
            HomeState(
                daysTogether = 0,
                shareMessage = "",
                todoList = persistentListOf(),
                isShowBottomSheet = true,
                isLoading = false,
            ),
            HomeState(
                daysTogether = 0,
                shareMessage = "",
                todoList = persistentListOf(),
                isShowBottomSheet = false,
                isLoading = false,
                coupleState = HomeState.CoupleState.DISCONNECT,
            ),
            HomeState(
                daysTogether = 0,
                shareMessage = "",
                todoList = persistentListOf(),
                isShowBottomSheet = false,
                isLoading = false,
            ),
            HomeState(
                daysTogether = 1120,
                shareMessage = "내일 여기서 만나",
                todoList = persistentListOf(),
                isShowBottomSheet = false,
                isLoading = false,
            ),
            HomeState(
                daysTogether = 1120,
                shareMessage = "내일 여기서 만나",
                todoList =
                    listOf(
                        ScheduleItem(
                            id = 1,
                            title = "오늘 캘린더에 첫 번째로 적혀있는 것이 표시됩니다. 글자수가 넘어가면 어떻게 되나요?",
                            contentAssignee = ContentAssignee.ME,
                        ),
                    ).toImmutableList(),
                isShowBottomSheet = false,
                isLoading = false,
            ),
            HomeState(
                daysTogether = 1120,
                shareMessage = "내일 여기서 만나",
                todoList =
                    (0..7)
                        .map {
                            ScheduleItem(
                                id = it.toLong(),
                                title = "오늘 캘린더에 첫 번째로 적혀있는 것이 표시됩니다. 글자수가 넘어가면 어떻게 되나요?",
                                contentAssignee = ContentAssignee.US,
                            )
                        }.toImmutableList(),
                isShowBottomSheet = false,
                isLoading = false,
            ),
            HomeState(
                daysTogether = 1120,
                shareMessage = "내일 여기서 만나",
                todoList =
                    (0..7)
                        .map {
                            ScheduleItem(
                                id = it.toLong(),
                                title = "오늘 캘린더에 첫 번째로 적혀있는 것이 표시됩니다. 글자수가 넘어가면 어떻게 되나요?",
                                contentAssignee = ContentAssignee.PARTNER,
                            )
                        }.toImmutableList(),
                isShowBottomSheet = false,
                isLoading = true,
            ),
        )
}
