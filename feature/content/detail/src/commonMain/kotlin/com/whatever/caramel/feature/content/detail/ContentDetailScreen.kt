package com.whatever.caramel.feature.content.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
    onIntent: (ContentDetailIntent) -> Unit
) {
    val uriHandler = LocalUriHandler.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CaramelTheme.color.background.primary,
        topBar = {
            Column(modifier = Modifier.statusBarsPadding()) {
                CaramelTopBar(
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable {
                                onIntent(ContentDetailIntent.ClickDeleteButton)
                            },
                            painter = painterResource(resource = Resources.Icon.ic_trash_24),
                            tint = CaramelTheme.color.icon.primary,
                            contentDescription = "Delete"
                        )
                        Spacer(modifier = Modifier.padding(start = 20.dp))
                        Icon(
                            modifier = Modifier.clickable {
                                onIntent(ContentDetailIntent.ClickEditButton)
                            },
                            painter = painterResource(resource = Resources.Icon.ic_edit_line_24),
                            tint = CaramelTheme.color.icon.primary,
                            contentDescription = "Edit"
                        )
                    }
                )
                TitleTextField(
                    modifier = Modifier.padding(horizontal = CaramelTheme.spacing.xl),
                    value = state.title,
                    onValueChange = {},
                    onKeyboardAction = {},
                    readOnly = true,
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = CaramelTheme.spacing.xl),
                    color = CaramelTheme.color.divider.primary
                )
            }
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(horizontal = CaramelTheme.spacing.xl)
                .fillMaxSize()
        ) {
            TextWithUrlPreview(
                text = state.description,
                linkMetaData = state.linkMetaDataList.toList(),
                onLinkPreviewClick = {
                    uriHandler.openUri(it)
                }
            )
        }
    }
} 