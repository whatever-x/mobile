package com.whatever.caramel.feature.memo.component.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.feature.memo.component.skeleton.LoadingTagList
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun TagList(
    modifier: Modifier = Modifier,
    isTagLoading: Boolean,
    lazyRowState: LazyListState,
    tagList: ImmutableList<Tag>,
    selectedTag: Tag?,
    onClickChip: (Tag) -> Unit,
) {
    if (isTagLoading) {
        LoadingTagList(modifier = modifier)
    } else {
        LazyRow(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(top = CaramelTheme.spacing.xs)
                    .padding(bottom = CaramelTheme.spacing.m),
            horizontalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.s),
            state = lazyRowState,
        ) {
            itemsIndexed(
                items = tagList,
                key = { index, tag -> "$index-${tag.id}" },
            ) { index, tag ->
                TagChip(
                    modifier =
                        when (index) {
                            0 -> Modifier.padding(start = CaramelTheme.spacing.xl)
                            tagList.lastIndex -> Modifier.padding(end = CaramelTheme.spacing.xl)
                            else -> Modifier
                        },
                    tag = tag,
                    isSelected = selectedTag == tag,
                    onClickChip = onClickChip,
                )
            }
        }
    }
}
