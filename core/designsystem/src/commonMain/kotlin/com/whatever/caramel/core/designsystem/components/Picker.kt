package com.whatever.caramel.core.designsystem.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.PickerScrollMode.BOUNDED
import com.whatever.caramel.core.designsystem.components.PickerScrollMode.LOOPING
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import kotlin.math.abs

@Composable
fun <T> rememberPickerState(initialItem: T) = remember { PickerState(initialItem) }

class PickerState<T>(
    initialItem: T
) {
    var selectedItem by mutableStateOf(initialItem)
}

/**
 * @property [LOOPING] 반복 스크롤 모드
 * @property [BOUNDED] 시작-끝 스크롤 모드
 * @author GunHyung-Ham
 * @since 2025.04.02
 */
enum class PickerScrollMode {
    LOOPING,
    BOUNDED
}

/**
 * @param modifier CaramelTextWheelPicker에 적용되는 Modifier 입니다.
 * @param items 피커에 표시할 항목 목록입니다.
 * @param state 선택된 아이템을 저장하고 외부와 공유할 [PickerState]입니다.
 * @param visibleItemsCount 화면에 동시에 보여질 아이템 개수입니다.
 * @param itemSpacing 아이템 간의 간격입니다.
 * @param textStyle 선택되지 않은 아이템에 적용할 [TextStyle]입니다.
 * @param dividerThickness Divider의 두께입니다. Divider 두께에 따라 아이템 간격에 영향을 미칩니다.
 * @param dividerWidth Divider의 너비입니다. Divider 너비에 따라 피커의 레이아웃이 결정됩니다.
 * @param dividerColor Divider의 색상입니다.
 * @param scrollMode 피커의 스크롤 동작 모드입니다.
 * @param onItemSelected 아이템 선택 시 호출되는 콜백 함수입니다. 선택된 아이템을 매개변수로 전달합니다.
 *
 * @author GunHyung-Ham
 * @since 2025.04.02
 */
@Composable
fun <T> CaramelTextWheelPicker(
    modifier: Modifier = Modifier,
    items: List<T>,
    state: PickerState<T>,
    visibleItemsCount: Int = 3,
    itemSpacing: Dp = 8.dp,
    textStyle: TextStyle = CaramelTheme.typography.heading2,
    dividerThickness: Dp = 2.dp,
    dividerWidth: Dp = 50.dp,
    dividerColor: Color = CaramelTheme.color.fill.quaternary,
    scrollMode: PickerScrollMode = LOOPING,
    onItemSelected: (T) -> Unit,
) {
    fun getItem(index: Int): T = items[index % items.size]

    fun <T> getInitialFirstVisibleItemIndex(
        items: List<T>,
        state: PickerState<T>,
        visibleItemsCount: Int,
        scrollMode: PickerScrollMode
    ): Int =
        when (scrollMode) {
            BOUNDED -> items.indexOf(state.selectedItem).coerceAtLeast(0)
            LOOPING -> {
                val visibleItemsMiddle = visibleItemsCount / 2
                val listScrollCount = Int.MAX_VALUE
                val listScrollMiddle = listScrollCount / 2
                val selectedIndex = items.indexOf(state.selectedItem).coerceAtLeast(0)

                listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + selectedIndex
            }
        }

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex =
            getInitialFirstVisibleItemIndex(items, state, visibleItemsCount, scrollMode)
    )
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    // @ham2174 TODO : MVP 이후 리스트 업데이트 시 이전 선택된 값과 가까운 인덱스로 변경하는 로직 구현
    val selectedItemIndex by remember {
        derivedStateOf {
            val center = listState.layoutInfo.viewportStartOffset +
                    (listState.layoutInfo.viewportEndOffset - listState.layoutInfo.viewportStartOffset) / 2

            listState.layoutInfo.visibleItemsInfo.minByOrNull { item ->
                val itemCenter = item.offset + item.size / 2
                abs(itemCenter - center)
            }?.index ?: 0
        }
    }

    LaunchedEffect(items, selectedItemIndex) {
        val item = getItem(selectedItemIndex)

        if (item != state.selectedItem) {
            state.selectedItem = item
            onItemSelected(item)
        }
    }

    SubcomposeLayout(modifier = modifier) { constraints ->
        val itemPlaceable = subcompose("item") {
            Text(
                text = items.firstOrNull()?.toString().orEmpty(),
                style = textStyle,
            )
        }.first().measure(constraints)

        val itemHeight = itemPlaceable.measuredHeight.toDp()
        val itemSpace = itemSpacing + dividerThickness
        val contentVerticalPadding = itemHeight + itemSpace
        val lazyColumnHeight =
            (itemHeight * visibleItemsCount) + (itemSpace * (visibleItemsCount - 1))

        val lazyColumnPlaceable = subcompose("list") {
            Box {
                LazyColumn(
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .height(height = lazyColumnHeight),
                    contentPadding = PaddingValues(
                        vertical = when (scrollMode) {
                            LOOPING -> 0.dp
                            BOUNDED -> contentVerticalPadding
                        }
                    ),
                    state = listState,
                    flingBehavior = flingBehavior,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = itemSpace
                    ),
                ) {
                    items(
                        count = when (scrollMode) {
                            LOOPING -> Int.MAX_VALUE
                            BOUNDED -> items.size
                        },
                        key = { it -> getItem(it).hashCode() },
                    ) { index ->
                        Text(
                            text = getItem(index).toString(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = textStyle,
                            textAlign = TextAlign.Center,
                            color = if (index == selectedItemIndex) {
                                CaramelTheme.color.text.primary
                            } else {
                                CaramelTheme.color.text.tertiary
                            },
                        )
                    }
                }

                if (dividerThickness != 0.dp) {
                    val visibleItemsMiddle = visibleItemsCount / 2
                    val topDividerOffset =
                        ((itemHeight * visibleItemsMiddle) + (itemSpace * visibleItemsMiddle)) - (itemSpace / 2) - (dividerThickness / 2)
                    val bottomDividerOffset =
                        ((itemHeight * (visibleItemsMiddle + 1)) + (itemSpace * visibleItemsMiddle) - dividerThickness) + (itemSpace / 2) + (dividerThickness / 2)

                    // Top-Divider
                    HorizontalDivider(
                        color = dividerColor,
                        thickness = dividerThickness,
                        modifier = Modifier
                            .offset(y = topDividerOffset)
                            .width(width = dividerWidth)
                    )

                    // Bottom-Divider
                    HorizontalDivider(
                        color = dividerColor,
                        thickness = dividerThickness,
                        modifier = Modifier
                            .offset(y = bottomDividerOffset)
                            .width(width = dividerWidth)
                    )
                }
            }
        }.first().measure(constraints)

        layout(lazyColumnPlaceable.width, lazyColumnPlaceable.height) {
            lazyColumnPlaceable.placeRelative(0, 0)
        }
    }
}
