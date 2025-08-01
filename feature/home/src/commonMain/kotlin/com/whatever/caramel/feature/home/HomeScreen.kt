package com.whatever.caramel.feature.home

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.CaramelDialog
import com.whatever.caramel.core.designsystem.components.CaramelPullToRefreshIndicator
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.components.DefaultCaramelDialogLayout
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.home.components.ShareMessageBottomSheet
import com.whatever.caramel.feature.home.components.header.disconnectedCard
import com.whatever.caramel.feature.home.components.header.header
import com.whatever.caramel.feature.home.components.quiz.quiz
import com.whatever.caramel.feature.home.components.todo.todo
import com.whatever.caramel.feature.home.mvi.HomeIntent
import com.whatever.caramel.feature.home.mvi.HomeState
import org.jetbrains.compose.resources.painterResource
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    val sheetState =
        rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = false,
        )

    if (state.isShowDialog) {
        CaramelDialog(
            show = state.isShowDialog,
            title = state.dialogTitle,
            mainButtonText = "확인",
            onDismissRequest = { onIntent(HomeIntent.HideDialog) },
            onMainButtonClick = { onIntent(HomeIntent.HideDialog) },
        ) {
            DefaultCaramelDialogLayout()
        }
    }

    if (state.isShowBottomSheet) {
        ShareMessageBottomSheet(
            bottomSheetContentModifier =
                Modifier
                    .padding(
                        top = CaramelTheme.spacing.l + 24.dp,
                        start = CaramelTheme.spacing.xl,
                        end = CaramelTheme.spacing.xl,
                    ),
            sheetState = sheetState,
            shareMessage = state.bottomSheetShareMessage,
            onDismiss = { onIntent(HomeIntent.HideShareMessageEditBottomSheet) },
            onClickSave = { onIntent(HomeIntent.SaveShareMessage) },
            messageLength = state.bottomSheetShareMessageLength,
            onInputShareMessage = { newShareMessage -> onIntent(HomeIntent.InputShareMessage(newShareMessage = newShareMessage)) },
            onClearMessage = { onIntent(HomeIntent.ClearShareMessage) },
        )
    }

    Scaffold(
        containerColor = CaramelTheme.color.background.primary,
        topBar = {
            CaramelTopBar(
                modifier = Modifier.statusBarsPadding(),
                trailingIcon = {
                    Icon(
                        modifier =
                            Modifier.clickable(
                                onClick = { onIntent(HomeIntent.ClickSettingButton) },
                                interactionSource = null,
                                indication = null,
                            ),
                        painter = painterResource(resource = Resources.Icon.ic_setting_24),
                        tint = CaramelTheme.color.icon.primary,
                        contentDescription = null,
                    )
                },
            )
        },
    ) { innerPadding ->
        val pullToRefreshState = rememberPullToRefreshState()
        val lazyListState = rememberLazyListState()
        val homeContentsOffset by animateIntAsState(
            targetValue =
                when {
                    state.isLoading -> 250
                    pullToRefreshState.distanceFraction in 0f..1f -> (250 * pullToRefreshState.distanceFraction).roundToInt()
                    pullToRefreshState.distanceFraction > 1f -> (250 + ((pullToRefreshState.distanceFraction - 1f) * 1f) * 100).roundToInt()
                    else -> 0
                },
        )

        PullToRefreshBox(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            state = pullToRefreshState,
            isRefreshing = state.isLoading,
            onRefresh = { onIntent(HomeIntent.PullToRefresh) },
            indicator = {
                CaramelPullToRefreshIndicator(
                    state = pullToRefreshState,
                    isRefreshing = state.isLoading,
                )
            },
        ) {
            LazyColumn(
                state = lazyListState,
                userScrollEnabled = !state.isLoading,
                overscrollEffect = null,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer { translationY = homeContentsOffset.toFloat() },
                verticalArrangement =
                    Arrangement.spacedBy(
                        space = CaramelTheme.spacing.xl,
                    ),
            ) {
                header(
                    shareMessage = state.shareMessage,
                    daysTogether = state.daysTogether,
                    onClickShareMessage = { onIntent(HomeIntent.ShowShareMessageEditBottomSheet) },
                )

                if (state.coupleState == HomeState.CoupleState.DISCONNECT) {
                    disconnectedCard()
                }

                quiz(
                    balanceGameCard = state.balanceGameCard,
                    isBalanceGameCardRotated = state.isBalanceGameCardRotated,
                    myNickname = state.myNickname,
                    myGender = state.myGender,
                    partnerNickname = state.partnerNickname,
                    partnerGender = state.partnerGender,
                    onOptionClick = { option -> onIntent(HomeIntent.ClickBalanceGameOptionButton(option = option)) },
                    onRotateCard = { onIntent(HomeIntent.RotateBalanceGameCard) },
                )

                todo(
                    todoList = state.todoList,
                    isSetAnniversary = state.isSetAnniversary,
                    onClickAnniversaryNudgeCard = { onIntent(HomeIntent.ClickAnniversaryNudgeCard) },
                    onClickTodoItem = { todoContentId ->
                        onIntent(HomeIntent.ClickTodoContent(todoContentId = todoContentId))
                    },
                    onClickEmptyTodo = { onIntent(HomeIntent.CreateTodoContent) },
                )
            }
        }
    }
}
