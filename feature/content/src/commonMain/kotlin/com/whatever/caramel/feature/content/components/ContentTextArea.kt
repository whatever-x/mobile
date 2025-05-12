package com.whatever.caramel.feature.content.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

/**
 * 에디터에서 사용할 수 있는 블록 타입을 정의
 */
sealed class EditorBlock {
    data class Text(var text: String) : EditorBlock()
    data class Url(var url: String) : EditorBlock()
}

private val urlRegex = Regex("""^https?://\S+$""")

@Composable
fun ContentTextArea(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    onLinkPreviewClick: (String) -> Unit,
    fetchLinkPreview: (String) -> LinkPreview = {
        LinkPreview.Loaded(
            title = "asdfasdf",
            imageUrl = "https://picsum.photos/50/50"
        )
    },
) {
    var blocks by remember { mutableStateOf(listOf<EditorBlock>(EditorBlock.Text(text = value))) }
    val focusRequester = remember { FocusRequester() }

    LazyColumn(
        modifier = modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitPointerEvent()
                        focusRequester.requestFocus()
                    }
                }
            }
    ) {
        itemsIndexed(blocks) { index, block ->
            Spacer(Modifier.height(8.dp))
            EditorBlockItem(
                block = block,
                onTextChange = { newText ->
                    blocks = blocks.handleTextChange(index, newText)
                    onValueChanged(newText)
                },
                placeholder = placeholder,
                onLinkPreviewClick = onLinkPreviewClick,
                fetchLinkPreview = fetchLinkPreview,
                focusRequester = focusRequester
            )
        }
    }
}

@Composable
private fun EditorBlockItem(
    block: EditorBlock,
    placeholder: String,
    onTextChange: (String) -> Unit,
    onLinkPreviewClick: (String) -> Unit,
    fetchLinkPreview: (String) -> LinkPreview,
    focusRequester: FocusRequester,
) {
    ContentTextField(
        value = when (block) {
            is EditorBlock.Text -> block.text
            is EditorBlock.Url -> block.url
        },
        placeholder = placeholder,
        onValueChange = onTextChange,
        isUrl = when (block) {
            is EditorBlock.Text -> urlRegex.matches(block.text.trim())
            is EditorBlock.Url -> true
        },
        focusRequester = focusRequester
    )
    if (block is EditorBlock.Url) {
        LinkPreviewCard(
            url = block.url,
            onClick = onLinkPreviewClick,
            fetchLinkMetadata = fetchLinkPreview
        )
    }
}

@Composable
private fun ContentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isUrl: Boolean,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
) {
    val baseTextStyle = CaramelTheme.typography.body2.reading
    val textStyle = if (isUrl) {
        baseTextStyle.copy(
            color = CaramelTheme.color.fill.brand,
            textDecoration = TextDecoration.Underline
        )
    } else {
        baseTextStyle.copy(
            color = CaramelTheme.color.text.primary
        )
    }

    val placeholderStyle = baseTextStyle.copy(
        color = CaramelTheme.color.text.secondary
    )

    Box(modifier = modifier.fillMaxSize()) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester),
            textStyle = textStyle,
            cursorBrush = SolidColor(CaramelTheme.color.fill.brand)
        )

        if (value.isEmpty()) {
            Text(
                text = placeholder,
                style = placeholderStyle
            )
        }
    }
}


@Composable
fun LinkPreviewCard(
    url: String,
    onClick: (String) -> Unit,
    fetchLinkMetadata: (url: String) -> LinkPreview,
) {
    val linkPreview by produceState<LinkPreview>(initialValue = LinkPreview.Loading, url) {
        value = fetchLinkMetadata(url)
    }

    AnimatedVisibility(linkPreview is LinkPreview.Loaded) {
        val metaData = linkPreview as LinkPreview.Loaded
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFFF0F0F0))
                .clickable(onClick = {
                    onClick(url)
                })
                .padding(8.dp)
        ) {
            AsyncImage(
                model = metaData.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = metaData.title,
                    style = CaramelTheme.typography.body2.regular,
                    color = CaramelTheme.color.text.primary
                )
            }
        }
    }
}

sealed interface LinkPreview {
    data object Loading : LinkPreview
    data class Loaded(
        val title: String,
        val imageUrl: String
    ) : LinkPreview
}


private fun List<EditorBlock>.handleTextChange(
    index: Int,
    newText: String
): List<EditorBlock> {
    return when {
        // 줄바꿈이 있는 경우 블록을 분리
        newText.contains("\n") -> handleNewLine(index, newText)
        // URL 형식인 경우 URL 블록으로 변환
        urlRegex.matches(newText.trim()) -> convertToUrlBlock(index, newText)
        // 일반 텍스트로 업데이트
        else -> updateTextBlock(index, newText)
    }
}

/**
 * 줄바꿈이 있는 텍스트를 처리하여 여러 블록으로 분리합니다.
 */
private fun List<EditorBlock>.handleNewLine(
    index: Int,
    newText: String
): List<EditorBlock> {
    val parts = newText.split("\n")
    val newBlocks = parts.map { EditorBlock.Text(it) }
        .flatMapIndexed { i, block ->
            if (i < parts.lastIndex) listOf(block, EditorBlock.Text(""))
            else listOf(block)
        }

    return toMutableList().apply {
        removeAt(index)
        addAll(index, newBlocks)
    }
}

private fun List<EditorBlock>.convertToUrlBlock(
    index: Int,
    newText: String
): List<EditorBlock> = toMutableList().apply {
    set(index, EditorBlock.Url(newText.trim()))
}

private fun List<EditorBlock>.updateTextBlock(
    index: Int,
    newText: String
): List<EditorBlock> = toMutableList().apply {
    set(index, EditorBlock.Text(newText))
}