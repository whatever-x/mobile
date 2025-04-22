package com.whatever.caramel.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.home.components.Header
import com.whatever.caramel.feature.home.components.Quiz
import com.whatever.caramel.feature.home.components.ShareMessageBottomSheet
import com.whatever.caramel.feature.home.components.Todo
import com.whatever.caramel.feature.home.mvi.HomeIntent
import com.whatever.caramel.feature.home.mvi.HomeState
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = false
    )

    if (state.isShowBottomSheet) {
        ShareMessageBottomSheet(
            bottomSheetContentModifier = Modifier
                .padding(
                    top = CaramelTheme.spacing.l + 24.dp,
                    start = CaramelTheme.spacing.xl,
                    end = CaramelTheme.spacing.xl
                ),
            sheetState = sheetState,
            shareMessage = state.bottomSheetMessage,
            onChangeShareMessage = { message -> onIntent(HomeIntent.ChangeShareMessage(message = message)) },
            onDismiss = { onIntent(HomeIntent.HideShareMessageEditBottomSheet) },
            onClickClear = { onIntent(HomeIntent.ClearShareMessage) },
            onClickSave = { onIntent(HomeIntent.SaveShareMessage) }
        )
    }

    Scaffold(
        containerColor = CaramelTheme.color.background.primary,
        topBar = {
            CaramelTopBar(
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable(
                            onClick = { onIntent(HomeIntent.ClickSettingButton) },
                            interactionSource = null,
                            indication = null
                        ),
                        painter = painterResource(resource = Resources.Icon.ic_setting_24),
                        tint = CaramelTheme.color.icon.primary,
                        contentDescription = null
                    )
                }
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            state = pullToRefreshState,
            isRefreshing = state.isLoading,
            onRefresh = { onIntent(HomeIntent.PullToRefresh) },
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                Header(
                    shareMessage = state.savedShareMessage,
                    daysTogether = state.daysTogether,
                    onClickShareMessage = { onIntent(HomeIntent.ShowShareMessageEditBottomSheet) }
                )

                Quiz(
                    // @hamn2174 TODO : 퀴즈 구현시 UI 추가
                )

                Todo(
                    todoList = state.todos.toImmutableList(),
                    isSetAnniversary = state.isSetAnniversary,
                    onClickAnniversaryNudgeCard = { onIntent(HomeIntent.ClickAnniversaryNudgeCard) },
                    onClickTodoItem = { todoContentId ->
                        onIntent(HomeIntent.ClickTodoContent(todoContentId = todoContentId))
                    },
                    onClickEmptyTodo = { onIntent(HomeIntent.CreateTodoContent) }
                )
            }
        }
    }
}