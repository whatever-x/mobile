package com.whatever.caramel.core.designsystem.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.snapshotFlow
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
import com.whatever.caramel.core.designsystem.util.HapticController
import com.whatever.caramel.core.designsystem.util.HapticStyle
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
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
 * @param startIndex 초기 선택 인덱스입니다.
 * @param visibleItemsCount 화면에 동시에 보여질 아이템 개수입니다.
 * @param itemSpacing 아이템 간의 간격입니다.
 * @param textStyle 선택되지 않은 아이템에 적용할 [TextStyle]입니다.
 * @param dividerColor Divider의 색상입니다.
 * @param scrollMode 피커의 스크롤 동작 모드입니다.
 * @param onHapticFeedback 아이템 변경 시 실행되는 햅틱 피드백 콜백입니다. [HapticController]를 주입받아 사용합니다.
 *
 * @author GunHyung-Ham
 * @since 2025.04.02
 */
@Composable
fun <T> CaramelTextWheelPicker(
    modifier: Modifier = Modifier,
    items: List<T>,
    state: PickerState<T>,
    startIndex: Int = 0,
    visibleItemsCount: Int = 3,
    itemSpacing: Dp = 8.dp,
    textStyle: TextStyle = CaramelTheme.typography.heading2,
    dividerColor: Color = CaramelTheme.color.fill.quaternary,
    scrollMode: PickerScrollMode = LOOPING,
    onHapticFeedback: (HapticStyle) -> Unit,
) {
    val visibleItemsMiddle = remember { visibleItemsCount / 2 }
    val adjustedItems = when (scrollMode) {
        LOOPING -> items
        BOUNDED -> listOf(null) + items + listOf(null)
    }

    val listScrollCount = when (scrollMode) {
        LOOPING -> Int.MAX_VALUE
        BOUNDED -> adjustedItems.size
    }

    val listScrollMiddle = remember { listScrollCount / 2 }
    val listStartIndex = remember {
        when (scrollMode) {
            LOOPING -> listScrollMiddle - listScrollMiddle % adjustedItems.size - visibleItemsMiddle + startIndex
            BOUNDED -> startIndex + 1
        }
    }

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    fun getItem(index: Int) = adjustedItems[index % adjustedItems.size]

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

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .mapNotNull { index -> getItem(index + visibleItemsMiddle) }
            .distinctUntilChanged()
            .collect { item ->
                onHapticFeedback(HapticStyle.GestureThresholdActivate)
                state.selectedItem = item
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
        val dividerThickness = 2.dp
        val lazyColumnHeight =
            (itemHeight * visibleItemsCount) + ((itemSpacing + dividerThickness) * (visibleItemsCount - 1))

        val lazyColumnPlaceable = subcompose("list") {
            Box(contentAlignment = Alignment.Center) {
                LazyColumn(
                    modifier = Modifier.height(height = lazyColumnHeight),
                    state = listState,
                    flingBehavior = flingBehavior,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = itemSpacing + dividerThickness
                    ),
                ) {
                    items(
                        count = listScrollCount,
                        key = { it },
                    ) { index ->
                        val currentItemText by remember {
                            mutableStateOf(if (getItem(index) == null) "" else getItem(index).toString())
                        }

                        Text(
                            text = currentItemText,
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

                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .height(height = itemHeight + itemSpacing + dividerThickness)
                ) {
                    // Top-Divider
                    HorizontalDivider(
                        color = dividerColor,
                        thickness = dividerThickness,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.TopCenter)
                    )

                    // Bottom-Divider
                    HorizontalDivider(
                        color = dividerColor,
                        thickness = dividerThickness,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.BottomCenter)
                    )
                }
            }
        }.first().measure(constraints)

        layout(lazyColumnPlaceable.width, lazyColumnPlaceable.height) {
            lazyColumnPlaceable.placeRelative(0, 0)
        }
    }
}
