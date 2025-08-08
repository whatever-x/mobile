package com.whatever.caramel.feature.content.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.shimmer
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.content.LinkMetaData
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ContentDetailDescription(
    modifier: Modifier = Modifier,
    description: String,
    linkMetaDataList: List<LinkMetaData>,
    onLinkPreviewClick: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement =
            Arrangement.spacedBy(
                space = CaramelTheme.spacing.xl,
                alignment = Alignment.Top,
            ),
    ) {
        HorizontalDivider(color = CaramelTheme.color.divider.primary)
        TextWithUrlPreview(
            text = description,
            linkMetaData = linkMetaDataList,
            onLinkPreviewClick = {
                onLinkPreviewClick(it)
            },
        )
    }
}

@Composable
internal fun ContentDetailTag(
    modifier: Modifier = Modifier,
    tagString: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement =
            Arrangement.spacedBy(
                space = CaramelTheme.spacing.xl,
                alignment = Alignment.Top,
            ),
    ) {
        HorizontalDivider(color = CaramelTheme.color.divider.primary)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =
                Arrangement.spacedBy(
                    space = CaramelTheme.spacing.s,
                    alignment = Alignment.Start,
                ),
        ) {
            Icon(
                painter = painterResource(resource = Resources.Icon.ic_tag_18),
                tint = CaramelTheme.color.icon.primary,
                contentDescription = null,
            )
            Text(
                text = tagString,
                style = CaramelTheme.typography.body2.regular,
                color = CaramelTheme.color.text.primary,
            )
        }
    }
}

@Composable
internal fun ContentDetailDate(
    modifier: Modifier = Modifier,
    dateText: String,
    timeText: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement =
            Arrangement.spacedBy(
                space = CaramelTheme.spacing.xl,
                alignment = Alignment.Top,
            ),
    ) {
        HorizontalDivider(color = CaramelTheme.color.divider.primary)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =
                Arrangement.spacedBy(
                    space = CaramelTheme.spacing.s,
                    alignment = Alignment.Start,
                ),
        ) {
            Icon(
                painter = painterResource(resource = Resources.Icon.ic_calendar_18),
                tint = CaramelTheme.color.icon.primary,
                contentDescription = null,
            )
            Text(
                text = dateText,
                style = CaramelTheme.typography.body2.regular,
                color = CaramelTheme.color.text.primary,
            )
            Text(
                text = timeText,
                style = CaramelTheme.typography.body2.regular,
                color = CaramelTheme.color.text.primary,
            )
        }
    }
}

@Composable
internal fun ContentDetailInfoSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement =
            Arrangement.spacedBy(
                space = CaramelTheme.spacing.xl,
                alignment = Alignment.Top,
            ),
    ) {
        HorizontalDivider(color = CaramelTheme.color.divider.primary)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.s),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(height = 23.dp)
                        .shimmer(shape = CaramelTheme.shape.xs),
            )
            Box(
                modifier =
                    Modifier
                        .size(width = 200.dp, height = 23.dp)
                        .shimmer(shape = CaramelTheme.shape.xs),
            )
        }
    }
}
