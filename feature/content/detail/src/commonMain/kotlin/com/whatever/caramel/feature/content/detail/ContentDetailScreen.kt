package com.whatever.caramel.feature.content.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CaramelTheme.color.background.primary,
        topBar = {
            Column(modifier = Modifier.statusBarsPadding()) {
                CaramelTopBar(
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
                TitleTextField(
                    modifier =
                        Modifier
                            .padding(top = CaramelTheme.spacing.s)
                            .padding(horizontal = CaramelTheme.spacing.xl),
                    value = state.title,
                    onValueChange = {},
                    onKeyboardAction = {},
                    readOnly = true,
                )
                if (state.scheduleDetail != null) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(top = CaramelTheme.spacing.xl)
                                .padding(horizontal = CaramelTheme.spacing.xl),
                        horizontalArrangement =
                            Arrangement.spacedBy(
                                space = CaramelTheme.spacing.s,
                                alignment = Alignment.Start,
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(resource = Resources.Icon.ic_calendar_18),
                            tint = CaramelTheme.color.icon.brand,
                            contentDescription = null,
                        )
                        Text(
                            text = state.date,
                            style = CaramelTheme.typography.body2.regular,
                            color = CaramelTheme.color.text.brand,
                        )
                        Text(
                            text = state.time,
                            style = CaramelTheme.typography.body2.regular,
                            color = CaramelTheme.color.text.brand,
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = CaramelTheme.spacing.xl),
                    color = CaramelTheme.color.divider.primary,
                )
            }
        },
        bottomBar = {
            LazyRow(
                modifier =
                    Modifier
                        .navigationBarsPadding()
                        .padding(bottom = 50.dp),
                contentPadding = PaddingValues(horizontal = CaramelTheme.spacing.xl),
                horizontalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.xs),
            ) {
                items(state.tags) { tag ->
                    Text(
                        text = "#${tag.label}",
                        style = CaramelTheme.typography.body2.reading,
                        color = CaramelTheme.color.text.brand,
                    )
                }
            }
        },
    ) { contentPadding ->
        Column(
            modifier =
                Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
        ) {
            TextWithUrlPreview(
                modifier = Modifier.padding(horizontal = CaramelTheme.spacing.xl),
                text = state.description,
                linkMetaData = state.linkMetaDataList.toList(),
                onLinkPreviewClick = {
                    uriHandler.openUri(it)
                },
            )
        }
    }
}
