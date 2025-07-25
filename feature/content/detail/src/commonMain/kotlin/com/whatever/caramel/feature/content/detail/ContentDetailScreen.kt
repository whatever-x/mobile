package com.whatever.caramel.feature.content.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.ui.content.TitleTextField
import com.whatever.caramel.feature.content.detail.components.TextWithUrlPreview
import com.whatever.caramel.feature.content.detail.mvi.ContentDetailIntent
import com.whatever.caramel.feature.content.detail.mvi.ContentDetailState
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ContentDetailScreen(
    state: ContentDetailState,
    onIntent: (ContentDetailIntent) -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)
            .background(color = CaramelTheme.color.background.primary)
    ) {
        CaramelTopBar(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = CaramelTheme.spacing.l),
            leadingContent = {
                Icon(
                    modifier =
                        Modifier
                            .clickable(
                                onClick = { onIntent(ContentDetailIntent.ClickBackButton) },
                                interactionSource = null,
                                indication = null,
                            ),
                    painter = painterResource(resource = Resources.Icon.ic_arrow_left_24),
                    contentDescription = null,
                    tint = CaramelTheme.color.icon.primary,
                )
            },
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier =
                            Modifier.clickable {
                                onIntent(ContentDetailIntent.ClickDeleteButton)
                            },
                        painter = painterResource(resource = Resources.Icon.ic_trash_24),
                        tint = CaramelTheme.color.icon.primary,
                        contentDescription = "Delete",
                    )
                    Spacer(modifier = Modifier.padding(start = 20.dp))
                    Icon(
                        modifier =
                            Modifier.clickable {
                                onIntent(ContentDetailIntent.ClickEditButton)
                            },
                        painter = painterResource(resource = Resources.Icon.ic_edit_line_24),
                        tint = CaramelTheme.color.icon.primary,
                        contentDescription = "Edit",
                    )
                }
            },
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = CaramelTheme.spacing.xl)
                .verticalScroll(verticalScrollState),
        ) {
            TitleTextField(
                modifier =
                    Modifier.padding(top = CaramelTheme.spacing.m),
                value = state.title,
                onValueChange = {},
                onKeyboardAction = {},
                readOnly = true,
            )
            if (state.existsContent) {
                HorizontalDivider(
                    modifier = Modifier.padding(top = CaramelTheme.spacing.l),
                    color = CaramelTheme.color.divider.primary
                )
            }
            if (state.scheduleDetail != null) {
                Row(
                    modifier = Modifier
                        .padding(vertical = CaramelTheme.spacing.l)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = CaramelTheme.spacing.s,
                        alignment = Alignment.Start
                    )
                ) {
                    Icon(
                        painter = painterResource(resource = Resources.Icon.ic_calendar_18),
                        tint = CaramelTheme.color.icon.primary,
                        contentDescription = null,
                    )

                    Text(
                        text = state.date,
                        style = CaramelTheme.typography.body2.regular,
                        color = CaramelTheme.color.text.primary,
                    )
                    Text(
                        text = state.time,
                        style = CaramelTheme.typography.body2.regular,
                        color = CaramelTheme.color.text.primary,
                    )
                }
                HorizontalDivider(color = CaramelTheme.color.divider.primary)
            }
            if (state.tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = CaramelTheme.spacing.l),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        painter = painterResource(resource = Resources.Icon.ic_calendar_18),
                        tint = CaramelTheme.color.icon.primary,
                        contentDescription = null,
                    )
                    Text(
                        text = state.tagString,
                        style = CaramelTheme.typography.body2.regular,
                        color = CaramelTheme.color.text.primary,
                    )
                }
            }
            TextWithUrlPreview(
                text = state.description,
                linkMetaData = state.linkMetaDataList.toList(),
                onLinkPreviewClick = {
                    uriHandler.openUri(it)
                },
            )
        }
    }
}
