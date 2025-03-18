package com.whatver.caramel.core.ui.component

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.lerp
import com.whatever.caramel.core.designsystem.util.pixelsToDp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.math.abs

@Composable
fun Picker(
    modifier: Modifier = Modifier,
    items: List<String>,
    state: PickerState = rememberPickerState(),
    startIndex: Int = 0,
    visibleItemCount: Int = 5,
    selectedTextStyle: TextStyle = LocalTextStyle.current,
    selectedTextColor : Color = Color.Unspecified,
    textStyle: TextStyle = LocalTextStyle.current,
    textColor : Color = Color.Unspecified,
    textModifier: Modifier = Modifier,
    content: @Composable (String) -> Unit
) {
    val density = LocalDensity.current
    val visibleItemsMiddle = visibleItemCount / 2
    val listScrollCount = Int.MAX_VALUE
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex =
        listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex

    fun getItem(index: Int) = items[index % items.size]

    val scrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = listStartIndex
    )
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = scrollState)

    var itemHeightPixel by remember { mutableStateOf(0) }
    val itemHeightToDp = itemHeightPixel.pixelsToDp()

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex }
            .map { index -> getItem(index + visibleItemsMiddle) }
            .distinctUntilChanged()
            .collect { item ->
                state.selectedItem = item
            }
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = scrollState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize()
                .height(itemHeightToDp * visibleItemCount)
                .fadingEdge(fadingEdgeGradient)
        ) {
            items(listScrollCount) { index ->
                val fraction by remember {
                    derivedStateOf {
                        val currentItem =
                            scrollState.layoutInfo.visibleItemsInfo.firstOrNull { it.key == index }
                        currentItem?.offset?.let { offset ->
                            val itemHeightPx = with(density) { itemHeightToDp.toPx() }
                            val fraction =
                                (offset - itemHeightPx * visibleItemsMiddle) / itemHeightPx
                            abs(fraction.coerceIn(-1f, 1f))
                        } ?: 0f
                    }
                }

                Text(
                    text = getItem(index),
                    textAlign = TextAlign.Center,
                    style = textStyle.copy(
                        fontSize = lerp(
                            selectedTextStyle.fontSize,
                            textStyle.fontSize,
                            fraction
                        ),
                        color = lerp(
                            selectedTextColor,
                            textColor,
                            fraction
                        )
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .onSizeChanged { size -> itemHeightPixel = size.height }
                        .then(textModifier)
                )
            }
        }
    }
    content(state.selectedItem)
}

private fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

class PickerState {
    var selectedItem by mutableStateOf("")
}

@Composable
fun rememberPickerState() = remember {
    PickerState()
}
