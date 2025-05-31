package com.whatever.caramel.feature.content.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.common.LinkMetaData

@Composable
fun TextWithUrlPreview(
    modifier: Modifier = Modifier,
    text: String,
    linkMetaData: List<LinkMetaData>,
    onLinkPreviewClick: (String) -> Unit,
) {
    val urlRegex = Regex("""(https?://\S+)""")
    val parts = remember(text, linkMetaData) {
        val matches = urlRegex.findAll(text).toList()
        val result = mutableListOf<Any>()
        var lastIndex = 0

        val urlToPreviewMap = linkMetaData.associateBy { it.url }

        matches.forEach { matchResult ->
            if (matchResult.range.first > lastIndex) {
                result.add(text.substring(lastIndex, matchResult.range.first))
            }
            val urlString = matchResult.value
            result.add(urlToPreviewMap[urlString] ?: urlString)
            lastIndex = matchResult.range.last + 1
        }
        if (lastIndex < text.length) {
            result.add(text.substring(lastIndex))
        }
        result
    }

    Column(modifier = modifier) {
        parts.forEach { item ->
            when (item) {
                is String -> {
                    if (urlRegex.matches(item)) {
                        val annotatedString = buildAnnotatedString {
                            pushStyle(
                                SpanStyle(
                                    color = CaramelTheme.color.text.primary,
                                    textDecoration = TextDecoration.Underline
                                )
                            )
                            append(item)
                            pop()
                        }
                        ClickableText(
                            text = annotatedString,
                            onClick = { onLinkPreviewClick(item) }
                        )
                    } else {
                        Text(
                            text = item,
                            style = CaramelTheme.typography.body1.regular,
                            color = CaramelTheme.color.text.primary
                        )
                    }
                }

                is LinkMetaData -> {
                    val annotatedLinkString = buildAnnotatedString {
                        pushStyle(
                            SpanStyle(
                                color = CaramelTheme.color.text.primary,
                                textDecoration = TextDecoration.Underline
                            )
                        )
                        append(item.url)
                        pop()
                    }
                    ClickableText(
                        text = annotatedLinkString,
                        onClick = { onLinkPreviewClick(item.url) }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LinkPreview(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .clickable { onLinkPreviewClick(item.url) },
                        title = item.title,
                        imageUrl = item.imageUrl
                    )
                }
            }
        }
    }
}

