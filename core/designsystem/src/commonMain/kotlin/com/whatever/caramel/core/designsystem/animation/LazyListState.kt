package com.whatever.caramel.core.designsystem.animation

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.LazyListState

/**
 * 지정한 [itemIndex]에 해당하는 아이템을 LazyList의 뷰포트 중앙에 오도록
 * 부드럽게 스크롤합니다.
 *
 * - 아이템이 현재 뷰포트에 보이면,
 *   아이템의 중심과 뷰포트 중심의 상대 거리(offset)를 계산해
 *   해당 거리만큼 애니메이션 스크롤을 수행합니다.
 * - 아이템이 뷰포트에 없으면,
 *   먼저 아이템이 보이도록 스크롤한 뒤 다시 offset을 계산해
 *   뷰포트 중앙에 정확히 맞도록 위치를 조정합니다.
 *
 * 주로 LazyRow에서 Chip이나 Card 등을 클릭했을 때
 * 선택된 아이템을 중앙에 정렬할 때 유용합니다.
 *
 * @param itemIndex LazyList에서 중앙에 맞출 아이템의 인덱스
 */
suspend fun LazyListState.animateScrollToItemCenter(itemIndex: Int) {
    val offset = layoutInfo.resolveItemOffsetToCenter(index = itemIndex)
    if (offset != null) {
        animateScrollBy(offset.toFloat())
        return
    }

    scrollToItem(index = itemIndex)

    layoutInfo.resolveItemOffsetToCenter(index = itemIndex)?.let {
        animateScrollBy(it.toFloat())
    }
}

/**
 * LazyList에서 지정한 [index] 아이템을 뷰포트 중앙에 배치하기 위해
 * 필요한 가로 스크롤 offset(픽셀)을 계산합니다.
 *
 * 아이템이 현재 뷰포트에 보이면,
 * 아이템의 중심과 뷰포트 중심 사이의 거리를 계산해 반환합니다.
 * 이 offset을 scroll 함수에 넘겨주면 아이템이 중앙에 오도록 이동할 수 있습니다.
 *
 * 아이템이 현재 뷰포트에 없으면 null을 반환합니다.
 *
 * @param index 중앙으로 배치할 아이템의 인덱스
 * @return 아이템을 중앙에 맞추기 위한 offset(픽셀), 아이템이 안 보이면 null
 */
private fun LazyListLayoutInfo.resolveItemOffsetToCenter(index: Int): Int? {
    val itemInfo = visibleItemsInfo.firstOrNull { it.index == index } ?: return null
    val viewportCenter = viewportStartOffset + (viewportSize.width / 2)
    val itemCenter = itemInfo.offset + (itemInfo.size / 2)

    return itemCenter - viewportCenter
}