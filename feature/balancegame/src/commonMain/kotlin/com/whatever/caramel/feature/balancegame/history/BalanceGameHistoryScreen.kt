package com.whatever.caramel.feature.balancegame.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import caramel.feature.balancegame.generated.resources.Res
import caramel.feature.balancegame.generated.resources.balance_game_history_title
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.balancegame.history.components.BalanceGameHistoryCard
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistoryIntent
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistoryState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun BalanceGameHistoryScreen(
    state: BalanceGameHistoryState,
    onIntent: (BalanceGameHistoryIntent) -> Unit,
) {
    val listState = rememberLazyListState()
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@derivedStateOf false
            val total = listState.layoutInfo.totalItemsCount
            total > 0 && lastVisible >= total - LOAD_MORE_PREFETCH_DISTANCE
        }
    }
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) onIntent(BalanceGameHistoryIntent.LoadMore)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = CaramelTheme.color.background.primary),
    ) {
        CaramelTopBar(
            modifier = Modifier.statusBarsPadding(),
            centerContents = {
                Text(
                    text = stringResource(resource = Res.string.balance_game_history_title),
                    style = CaramelTheme.typography.heading3,
                    color = CaramelTheme.color.text.primary,
                )
            },
            leadingContent = {
                Icon(
                    modifier =
                        Modifier.clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = { onIntent(BalanceGameHistoryIntent.ClickBackButton) },
                        ),
                    painter = painterResource(resource = Resources.Icon.ic_arrow_left_24),
                    tint = CaramelTheme.color.icon.primary,
                    contentDescription = null,
                )
            },
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding =
                PaddingValues(
                    start = CaramelTheme.spacing.xl,
                    end = CaramelTheme.spacing.xl,
                    top = CaramelTheme.spacing.l,
                    bottom = CaramelTheme.spacing.xxl,
                ),
            verticalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.m),
        ) {
            items(
                items = state.items,
                key = { it.gameId },
                contentType = { "history_item" },
            ) { item ->
                BalanceGameHistoryCard(
                    item = item,
                    expanded = state.expandedGameId == item.gameId,
                    myNickname = state.myNickname,
                    myGender = state.myGender,
                    partnerNickname = state.partnerNickname,
                    partnerGender = state.partnerGender,
                    onClickCard = { onIntent(BalanceGameHistoryIntent.ClickHistoryCard(item.gameId)) },
                    onClickShare = { onIntent(BalanceGameHistoryIntent.ClickShareResult(item.gameId)) },
                )
            }

            if (state.isLoadingMore) {
                item(key = "loading_footer", contentType = "loading") {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(CaramelTheme.spacing.l),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    }
}

private const val LOAD_MORE_PREFETCH_DISTANCE = 3
