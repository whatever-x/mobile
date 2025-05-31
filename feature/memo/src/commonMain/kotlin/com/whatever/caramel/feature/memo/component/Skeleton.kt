package com.whatever.caramel.feature.memo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.shimmer
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
internal fun MemoItemSkeleton(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        repeat(2) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = CaramelTheme.spacing.xl),
                verticalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.s)
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 127.dp, height = 22.dp)
                        .shimmer(shape = CaramelTheme.shape.xs)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .shimmer(shape = CaramelTheme.shape.xs)
                )
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = CaramelTheme.color.divider.primary
            )
        }
    }


}

@Composable
internal fun TagChipSkeleton(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = CaramelTheme.spacing.xs, bottom = CaramelTheme.spacing.m)
            .padding(horizontal = CaramelTheme.spacing.l),
        horizontalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.s)
    ) {
        repeat(2) {
            Box(
                modifier = Modifier
                    .size(width = 70.dp, height = 34.dp)
                    .shimmer(shape = CaramelTheme.shape.xl)
            )
        }
    }
}