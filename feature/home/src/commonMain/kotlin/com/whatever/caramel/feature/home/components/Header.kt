package com.whatever.caramel.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.painterResource

internal fun LazyListScope.Header(
    daysTogether: Int,
    shareMessage: String,
    onClickShareMessage: () -> Unit
) {
    item(key = "Header") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = CaramelTheme.spacing.l,
                    start = CaramelTheme.spacing.xl,
                    end = CaramelTheme.spacing.xl,
                ),
            verticalArrangement = Arrangement.spacedBy(space = 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val daysTogetherAnnotatedString = buildAnnotatedString {
                withStyle(style = CaramelTheme.typography.heading1.toSpanStyle()) {
                    append("우리가 만난지\n")
                }
                withStyle(style = CaramelTheme.typography.display.toSpanStyle()) {
                    append("${daysTogether}일")
                }
            }

            Text(
                text = daysTogetherAnnotatedString,
                textAlign = TextAlign.Center,
                color = CaramelTheme.color.text.primary
            )

            val shareMessageAnnotatedString = buildAnnotatedString {
                append(shareMessage.ifEmpty { "함께 기억할 말을 남겨보세요" })
                append(" ")
                appendInlineContent(id = "edit icon")
            }
            val inlineContentMap = mapOf(
                "edit icon" to InlineTextContent(
                    placeholder = Placeholder(
                        width = 16.sp,
                        height = 16.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                    ),
                    children = {
                        Icon(
                            painter = painterResource(resource = Resources.Icon.ic_edit_16),
                            contentDescription = null,
                            tint = CaramelTheme.color.text.secondary
                        )
                    },
                )
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = onClickShareMessage,
                        interactionSource = null,
                        indication = null
                    )
                    .padding(vertical = CaramelTheme.spacing.xs),
                text = shareMessageAnnotatedString,
                inlineContent = inlineContentMap,
                color = CaramelTheme.color.text.secondary,
                style = CaramelTheme.typography.body3.bold,
                textAlign = TextAlign.Center
            )
        }
    }
}