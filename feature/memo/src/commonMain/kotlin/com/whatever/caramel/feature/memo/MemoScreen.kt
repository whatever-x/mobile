package com.whatever.caramel.feature.memo

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import caramel.core.designsystem.generated.resources.Res
import caramel.core.designsystem.generated.resources.memo
import com.whatever.caramel.core.designsystem.animation.animateScrollToItemCenter
import com.whatever.caramel.core.designsystem.components.CaramelPullToRefreshIndicator
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.util.DateFormatter.formatWithSeparator
import com.whatever.caramel.feature.memo.component.memoList.EmptyMemoList
import com.whatever.caramel.feature.memo.component.memoList.MemoItem
import com.whatever.caramel.feature.memo.component.skeleton.LoadingMemoList
import com.whatever.caramel.feature.memo.component.tag.TagList
import com.whatever.caramel.feature.memo.mvi.MemoContentState
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

    LaunchedEffect(state.memoContent) {
        if (state.memoContent == MemoContentState.Loading) {
            lazyListState.scrollToItem(0)
        }
    }

    LaunchedEffect(state.selectedTag) {
        if (state.selectedTag != null) {
            lazyRowState.animateScrollToItemCenter(state.tagList.indexOf(state.selectedTag))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CaramelTheme.color.background.primary)
            .statusBarsPadding(),
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

        PullToRefreshBox(
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
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val maxHeight = maxHeight
                val stickyHeaderHeight = 52.dp
                val maxContentHeight = maxHeight - stickyHeaderHeight

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { translationY = memoScreenOffset.toFloat() },
                    state = lazyListState,
                ) {
                    stickyHeader {
                        TagList(
                            modifier = Modifier
                                .height(height = stickyHeaderHeight)
                                .background(color = CaramelTheme.color.background.primary),
                            isTagLoading = state.isTagLoading,
                            lazyRowState = lazyRowState,
                            tagList = state.tagList,
                            selectedTag = state.selectedTag,
                            onClickChip = { tag -> onIntent(MemoIntent.ClickTagChip(tag = tag)) },
                        )
                    }

                    when (state.memoContent) {
                        is MemoContentState.Loading -> item { LoadingMemoList() }
                        is MemoContentState.Empty -> {
                            item {
                                EmptyMemoList(
                                    maxContentHeight = maxContentHeight,
                                    onClickRecommendMemo = { title ->
                                        onIntent(MemoIntent.ClickRecommendMemo(title = title))
                                    }
                                )
                            }
                        }

                        is MemoContentState.Content -> {
                            itemsIndexed(
                                items = state.memoContent.memoList,
                                key = { index, memo -> memo.id }
                            ) { index, memo ->
                                MemoItem(
                                    id = memo.id,
                                    title = memo.contentData.title,
                                    description = memo.contentData.description,
                                    categoriesText = memo.tagList.joinToString(separator = ",") { it.label },
                                    createdDateText = memo.createdAt.formatWithSeparator(separator = "."),
                                    contentAssignee = memo.contentData.contentAssignee,
                                    onClickMemoItem = { memoId ->
                                        onIntent(MemoIntent.ClickMemo(memoId = memoId))
                                    },
                                )

                                if (index < state.memoContent.memoList.lastIndex) {
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
