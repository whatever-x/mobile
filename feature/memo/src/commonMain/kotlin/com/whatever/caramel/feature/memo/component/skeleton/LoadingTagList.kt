package com.whatever.caramel.feature.memo.component.skeleton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.shimmer
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
internal fun LoadingTagList(modifier: Modifier = Modifier) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(top = CaramelTheme.spacing.xs, bottom = CaramelTheme.spacing.m)
                .padding(horizontal = CaramelTheme.spacing.l),
        horizontalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.s),
    ) {
        repeat(2) {
            Box(
                modifier =
                    Modifier
                        .size(width = 70.dp, height = 34.dp)
                        .shimmer(shape = CaramelTheme.shape.xl),
            )
        }
    }
}
