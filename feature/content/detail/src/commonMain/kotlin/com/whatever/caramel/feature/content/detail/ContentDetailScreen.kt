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
import caramel.feature.content.detail.generated.resources.Res
import caramel.feature.content.detail.generated.resources.both
import caramel.feature.content.detail.generated.resources.memo
import caramel.feature.content.detail.generated.resources.my
import caramel.feature.content.detail.generated.resources.partner
import caramel.feature.content.detail.generated.resources.schedule
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.ui.content.TitleTextField
import com.whatever.caramel.feature.content.detail.components.TextWithUrlPreview
import com.whatever.caramel.feature.content.detail.mvi.ContentDetailIntent
import com.whatever.caramel.feature.content.detail.mvi.ContentDetailState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ContentDetailScreen(
    state: ContentDetailState,
    onIntent: (ContentDetailIntent) -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    val verticalScrollState = rememberScrollState()
    val (assigneeTextRes, assigneeTextColor) =
        when (state.role) {
            ContentAssignee.ME -> Res.string.my to CaramelTheme.color.text.labelAccent4
            ContentAssignee.PARTNER -> Res.string.partner to CaramelTheme.color.text.primary
            ContentAssignee.US -> Res.string.both to CaramelTheme.color.text.brand
        }
    val contentTypeRes =
        when (state.contentType) {
            ContentType.MEMO -> Res.string.memo
            ContentType.CALENDAR -> Res.string.schedule
        }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = CaramelTheme.color.background.primary),
    ) {
        CaramelTopBar(
            modifier =
                Modifier
                    .statusBarsPadding(),
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
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = CaramelTheme.spacing.xl)
                    .verticalScroll(verticalScrollState),
        ) {
            Text(
                modifier = Modifier.padding(top = CaramelTheme.spacing.xs),
                text = stringResource(assigneeTextRes) + " " + stringResource(contentTypeRes),
                color = assigneeTextColor,
                style = CaramelTheme.typography.body2.bold,
            )
            TitleTextField(
                modifier =
                    Modifier.padding(top = CaramelTheme.spacing.m),
                value = state.title,
                onValueChange = {},
                onKeyboardAction = {},
                readOnly = true,
            )

            Column(
                modifier = Modifier.padding(top = CaramelTheme.spacing.xl),
                verticalArrangement =
                    Arrangement.spacedBy(
                        space = CaramelTheme.spacing.xl,
                        alignment = Alignment.Top,
                    ),
            ) {
                if (state.scheduleDetail != null) {
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
                }
                if (state.tags.isNotEmpty()) {
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
                            text = state.tagString,
                            style = CaramelTheme.typography.body2.regular,
                            color = CaramelTheme.color.text.primary,
                        )
                    }
                }
                if (state.description.isNotEmpty()) {
                    HorizontalDivider(color = CaramelTheme.color.divider.primary)
                    TextWithUrlPreview(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 50.dp),
                        text = state.description,
                        linkMetaData = state.linkMetaDataList.toList(),
                        onLinkPreviewClick = {
                            uriHandler.openUri(it)
                        },
                    )
                }
            }
        }
    }
}
