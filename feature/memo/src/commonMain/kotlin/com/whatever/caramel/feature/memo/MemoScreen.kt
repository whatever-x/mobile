package com.whatever.caramel.feature.memo

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import caramel.feature.memo.generated.resources.Res
import caramel.feature.memo.generated.resources.memo
import com.whatever.caramel.core.designsystem.animation.animateScrollToItemCenter
import com.whatever.caramel.core.designsystem.components.CaramelPullToRefreshIndicator
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.memo.component.EmptyMemo
import com.whatever.caramel.feature.memo.component.MemoItem
import com.whatever.caramel.feature.memo.component.MemoItemSkeleton
import com.whatever.caramel.feature.memo.component.TagChip
import com.whatever.caramel.feature.memo.component.TagChipSkeleton
import com.whatever.caramel.feature.memo.mvi.MemoIntent
import com.whatever.caramel.feature.memo.mvi.MemoState
import kotlinx.coroutines.flow.filter
import org.jetbrains.compose.resources.stringResource
import kotlin.math.max
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MemoScreen(
    state: MemoState,
    onIntent: (MemoIntent) -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val scrollState = rememberScrollState()
    val lazyRowState = rememberLazyListState()
    val lazyListState =
        rememberLazyListState().apply {
            onLastReached(
                numberOfItemsBeforeEnd = 3,
                onReachedNumberOfItemsBeforeEnd = { onIntent(MemoIntent.ReachedEndOfList) },
            )
        }
    val memoScreenOffset by animateIntAsState(
        targetValue =
            when {
                state.isRefreshing -> 250
                pullToRefreshState.distanceFraction in 0f..1f -> (250 * pullToRefreshState.distanceFraction).roundToInt()
                pullToRefreshState.distanceFraction > 1f -> (250 + ((pullToRefreshState.distanceFraction - 1f) * 1f) * 100).roundToInt()
                else -> 0
            },
    )

    LaunchedEffect(state.isMemoLoading) {
        if (state.isMemoLoading) {
            lazyListState.scrollToItem(0)
        }
    }

    LaunchedEffect(state.selectedChipIndex) {
        lazyRowState.animateScrollToItemCenter(state.selectedChipIndex)
    }

    PullToRefreshBox(
        modifier =
            Modifier
                .background(color = CaramelTheme.color.background.primary)
                .statusBarsPadding(),
        state = pullToRefreshState,
        isRefreshing = state.isRefreshing,
        onRefresh = { onIntent(MemoIntent.PullToRefresh) },
        indicator = {
            CaramelPullToRefreshIndicator(
                state = pullToRefreshState,
                isRefreshing = state.isRefreshing,
            )
        },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        translationY = memoScreenOffset.toFloat()
                    }.verticalScroll(scrollState),
        ) {
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = CaramelTheme.spacing.xl),
                text = stringResource(Res.string.memo),
                style = CaramelTheme.typography.heading1,
                color = CaramelTheme.color.text.primary,
            )
            if (state.isTagLoading) {
                TagChipSkeleton()
            } else {
                LazyRow(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = CaramelTheme.spacing.xs)
                            .padding(bottom = CaramelTheme.spacing.m),
                    horizontalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.s),
                    state = lazyRowState,
                ) {
                    itemsIndexed(state.tags, key = { index, tag ->
                        "$index-${tag.id}"
                    }) { index, tag ->
                        TagChip(
                            modifier =
                                when (index) {
                                    0 -> Modifier.padding(start = CaramelTheme.spacing.xl)
                                    state.tags.lastIndex -> Modifier.padding(end = CaramelTheme.spacing.xl)
                                    else -> Modifier
                                },
                            tag = tag,
                            isSelected = state.selectedTag == tag,
                            onClickChip = {
                                onIntent(
                                    MemoIntent.ClickTagChip(
                                        tag = it,
                                        index = index,
                                    ),
                                )
                            },
                        )
                    }
                }
            }
            if (state.isEmpty) {
                EmptyMemo(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),
                )
            } else {
                if (state.isMemoLoading) {
                    MemoItemSkeleton()
                } else {
                    LazyColumn(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .weight(1f),
                        state = lazyListState,
                    ) {
                        itemsIndexed(state.memos, key = { index, memo ->
                            memo.id
                        }) { index, memo ->
                            MemoItem(
                                id = memo.id,
                                title = memo.title,
                                description = memo.description,
                                categoriesText = memo.tagListText,
                                createdDateText = memo.createdAt,
                                onClickMemoItem = { onIntent(MemoIntent.ClickMemo(memoId = it)) },
                                assignee = memo.assignee,
                            )
                            if (index < state.memos.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(),
                                    thickness = 1.dp,
                                    color = CaramelTheme.color.divider.primary,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun LazyListState.onLastReached(
    numberOfItemsBeforeEnd: Int = 1,
    onReachedNumberOfItemsBeforeEnd: () -> Unit,
) {
    require(numberOfItemsBeforeEnd >= 0) { "Number of items before end must be greater than or equal to 0" }

    val lastItemVisible =
        remember {
            derivedStateOf {
                val totalItemsCount = layoutInfo.totalItemsCount
                val lastVisibleItemIndex =
                    (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
                totalItemsCount > 0 &&
                    lastVisibleItemIndex >=
                    max(
                        a = (totalItemsCount - numberOfItemsBeforeEnd),
                        b = 0,
                    )
            }
        }

    LaunchedEffect(lastItemVisible) {
        snapshotFlow { lastItemVisible.value }
            .filter { it }
            .collect {
                onReachedNumberOfItemsBeforeEnd()
            }
    }
}
