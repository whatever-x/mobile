package com.whatever.caramel.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import caramel.feature.home.generated.resources.Res
import caramel.feature.home.generated.resources.day
import caramel.feature.home.generated.resources.leave_something_to_remember_together
import caramel.feature.home.generated.resources.we_dated
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

internal fun LazyListScope.header(
    daysTogether: Int,
    shareMessage: String,
    onClickShareMessage: () -> Unit,
) {
    item(key = "Header") {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CaramelTheme.spacing.xl)
                    .padding(bottom = CaramelTheme.spacing.l),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(resource = Res.string.we_dated),
                style = CaramelTheme.typography.heading1,
                color = CaramelTheme.color.text.primary,
            )

            Text(
                modifier = Modifier.offset(y = (-8).dp),
                text = stringResource(resource = Res.string.day, formatArgs = arrayOf(daysTogether)),
                style = CaramelTheme.typography.display,
                color = CaramelTheme.color.text.primary,
            )

            val shareMessageAnnotatedString =
                buildAnnotatedString {
                    append(shareMessage.ifEmpty { stringResource(resource = Res.string.leave_something_to_remember_together) })
                    append(" ")
                    appendInlineContent(id = "edit icon")
                }
            val inlineContentMap =
                mapOf(
                    "edit icon" to
                        InlineTextContent(
                            placeholder =
                                Placeholder(
                                    width = 16.sp,
                                    height = 16.sp,
                                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
                                ),
                            children = {
                                Icon(
                                    painter = painterResource(resource = Resources.Icon.ic_edit_16),
                                    contentDescription = null,
                                    tint = CaramelTheme.color.icon.secondary,
                                )
                            },
                        ),
                )

            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = onClickShareMessage,
                            interactionSource = null,
                            indication = null,
                        ),
                text = shareMessageAnnotatedString,
                inlineContent = inlineContentMap,
                color = CaramelTheme.color.text.secondary,
                style = CaramelTheme.typography.body3.bold,
                textAlign = TextAlign.Center,
            )
        }
    }
}
