package com.whatever.caramel.feature.content.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import com.whatever.caramel.core.designsystem.themes.CaramelTheme


@Composable
internal fun ContentTextArea(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
) {

    val linkRegex = remember { Regex("""https?://\S+""") }

    val linkColor = CaramelTheme.color.text.brand
    val linkStyle = remember {
        SpanStyle(
            color = linkColor,
            textDecoration = TextDecoration.Underline
        )
    }

    val annotatedString: AnnotatedString = remember(value) {
        value.annotateLinks(regex = linkRegex, linkStyle = linkStyle, linkTag = "URL")
    }

    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = CaramelTheme.typography.body2.reading.copy(
            color = CaramelTheme.color.text.primary
        ),
        cursorBrush = SolidColor(CaramelTheme.color.fill.brand),
        visualTransformation = {
            TransformedText(annotatedString, OffsetMapping.Identity)
        },
        onTextLayout = { textLayoutResult = it },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        decorationBox = { inner ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopStart
            ) {
                inner()
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = CaramelTheme.typography.body2.reading.copy(
                            color = CaramelTheme.color.text.disabledPrimary
                        )
                    )
                }
            }
        }
    )
}

private fun String.annotateLinks(
    regex: Regex,
    linkStyle: SpanStyle,
    linkTag: String = "URL"
): AnnotatedString {
    val sourceText = this
    return buildAnnotatedString {
        var currentIndex = 0
        regex.findAll(sourceText).forEach { matchResult ->
            val linkRange = matchResult.range
            val linkValue = matchResult.value

            if (linkRange.first > currentIndex) {
                append(sourceText.substring(currentIndex, linkRange.first))
            }

            withAnnotation(tag = linkTag, annotation = linkValue) {
                withStyle(style = linkStyle) {
                    append(linkValue)
                }
            }
            currentIndex = linkRange.last + 1
        }

        if (currentIndex < sourceText.length) {
            append(sourceText.substring(currentIndex))
        }
    }
}
