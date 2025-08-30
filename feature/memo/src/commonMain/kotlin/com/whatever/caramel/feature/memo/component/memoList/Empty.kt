package com.whatever.caramel.feature.memo.component.memoList

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import caramel.core.designsystem.generated.resources.Res
import caramel.core.designsystem.generated.resources.empty_memo
import caramel.core.designsystem.generated.resources.how_about_memo_topic
import caramel.core.designsystem.generated.resources.ic_plus_14
import caramel.core.designsystem.generated.resources.recommend_memo_list
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun EmptyMemoList(
    maxContentHeight: Dp,
    onClickRecommendMemo: (String) -> Unit,
) {
    SubcomposeLayout { constraints ->
        val minEmptyImageHeight = 380.dp
        val maxContentHeightPx = maxContentHeight.roundToPx()

        val recommendPlaceables =
            subcompose("recommendContainer") {
                RecommendMemoList(
                    modifier = Modifier.fillMaxWidth(),
                    onClickRecommendMemo = onClickRecommendMemo,
                )
            }.map { it.measure(constraints) }

        val recommendHeight = recommendPlaceables.maxOfOrNull { it.height } ?: 0

        val emptyConstraints =
            if (recommendHeight + minEmptyImageHeight.roundToPx() < maxContentHeightPx) {
                constraints.copy(
                    minHeight = maxContentHeightPx - recommendHeight,
                    maxHeight = maxContentHeightPx - recommendHeight,
                )
            } else {
                constraints.copy(minHeight = minEmptyImageHeight.roundToPx())
            }

        val emptyPlaceables =
            subcompose("emptyContainer") {
                EmptyImage(
                    modifier = Modifier.fillMaxWidth(),
                )
            }.map { it.measure(emptyConstraints) }

        val totalHeight = recommendHeight + emptyPlaceables.maxOf { it.height }

        layout(constraints.maxWidth, totalHeight) {
            var y = 0

            emptyPlaceables.forEach {
                it.placeRelative(0, y)
                y += it.height
            }
            recommendPlaceables.forEach {
                it.placeRelative(0, y)
                y += it.height
            }
        }
    }
}

@Composable
private fun EmptyImage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(Resources.Image.img_blank_memo),
            tint = Color.Unspecified,
            contentDescription = null,
        )

        Text(
            text = stringResource(Res.string.empty_memo),
            style = CaramelTheme.typography.body3.regular,
            color = CaramelTheme.color.text.primary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun RecommendMemoList(
    modifier: Modifier = Modifier,
    onClickRecommendMemo: (String) -> Unit,
) {
    Box(
        modifier =
            modifier
                .padding(horizontal = CaramelTheme.spacing.xl)
                .padding(bottom = CaramelTheme.spacing.xl),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = CaramelTheme.color.background.tertiary,
                        shape = CaramelTheme.shape.m,
                    ).fillMaxWidth()
                    .padding(all = CaramelTheme.spacing.xl),
            verticalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.l),
        ) {
            Text(
                text = stringResource(resource = Res.string.how_about_memo_topic),
                style = CaramelTheme.typography.body2.bold,
                color = CaramelTheme.color.text.primary,
            )

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.xs),
            ) {
                stringArrayResource(resource = Res.array.recommend_memo_list).forEach { item ->
                    RecommendMemo(
                        text = item,
                        onClickRecommendMemo = onClickRecommendMemo,
                    )
                }
            }
        }
    }
}

@Composable
private fun RecommendMemo(
    modifier: Modifier = Modifier,
    text: String,
    onClickRecommendMemo: (String) -> Unit,
) {
    Box(
        modifier =
            modifier
                .background(
                    color = CaramelTheme.color.fill.inverse,
                    shape = CaramelTheme.shape.s,
                ).border(
                    width = 1.dp,
                    color = CaramelTheme.color.fill.quaternary,
                    shape = CaramelTheme.shape.s,
                ).clip(shape = CaramelTheme.shape.s)
                .clickable(
                    onClick = { onClickRecommendMemo(text) },
                ).padding(horizontal = CaramelTheme.spacing.l)
                .padding(vertical = CaramelTheme.spacing.m),
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement =
                Arrangement.spacedBy(
                    space = CaramelTheme.spacing.m,
                ),
        ) {
            Text(
                text = text,
                style = CaramelTheme.typography.body3.regular,
                color = CaramelTheme.color.text.primary,
            )

            Icon(
                painter = painterResource(resource = Res.drawable.ic_plus_14),
                contentDescription = null,
                tint = CaramelTheme.color.icon.brand,
            )
        }
    }
}
