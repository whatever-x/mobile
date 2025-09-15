package com.whatever.caramel.feature.content.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.components.shimmer
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.ui.content.TitleTextField
import com.whatever.caramel.feature.content.detail.components.ContentAssigneeText
import com.whatever.caramel.feature.content.detail.components.ContentDetailDate
import com.whatever.caramel.feature.content.detail.components.ContentDetailDescription
import com.whatever.caramel.feature.content.detail.components.ContentDetailInfoSkeleton
import com.whatever.caramel.feature.content.detail.components.ContentDetailTag
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
            ContentAssigneeText(
                modifier = Modifier.padding(top = CaramelTheme.spacing.xs),
                contentType = state.contentType,
                contentAssignee = state.contentAssignee,
                isLoading = state.isLoading,
            )
            if (state.isLoading) {
                Box(
                    modifier =
                        Modifier
                            .padding(top = CaramelTheme.spacing.m)
                            .shimmer(shape = CaramelTheme.shape.xs)
                            .fillMaxWidth()
                            .height(height = 36.dp),
                )
            } else {
                TitleTextField(
                    modifier =
                        Modifier.padding(top = CaramelTheme.spacing.m),
                    value = state.title,
                    onValueChange = {},
                    onKeyboardAction = {},
                    readOnly = true,
                )
            }
            Column(
                modifier = Modifier.padding(top = CaramelTheme.spacing.xl),
                verticalArrangement =
                    Arrangement.spacedBy(
                        space = CaramelTheme.spacing.xl,
                        alignment = Alignment.Top,
                    ),
            ) {
                if (state.isLoading) {
                    ContentDetailInfoSkeleton(
                        modifier = Modifier.fillMaxWidth(),
                    )
                } else {
                    if (state.scheduleDetail != null) {
                        ContentDetailDate(
                            modifier = Modifier.fillMaxWidth(),
                            startDateTime = state.scheduleDetail.dateTimeInfo.startDateTime,
                            endDateTime = state.scheduleDetail.dateTimeInfo.endDateTime,
                            isAllDay = state.isAllDay,
                            isMultiDay = state.isMultiDay,
                        )
                    }
                    if (state.tags.isNotEmpty()) {
                        ContentDetailTag(
                            modifier = Modifier.fillMaxWidth(),
                            tagString = state.tagString,
                        )
                    }
                    if (state.description.isNotEmpty()) {
                        ContentDetailDescription(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 50.dp),
                            description = state.description,
                            linkMetaDataList = state.linkMetaDataList,
                            onLinkPreviewClick = {
                                uriHandler.openUri(it)
                            },
                        )
                    }
                }
            }
        }
    }
}
