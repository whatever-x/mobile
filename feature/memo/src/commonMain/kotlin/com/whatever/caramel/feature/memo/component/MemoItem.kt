package com.whatever.caramel.feature.memo.component

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import caramel.feature.memo.generated.resources.Res
import caramel.feature.memo.generated.resources.both_memo
import caramel.feature.memo.generated.resources.empty_memo
import caramel.feature.memo.generated.resources.my_memo
import caramel.feature.memo.generated.resources.partner_memo
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private val lineBreakRegex = Regex("\\r?\\n")

@Composable
internal fun MemoItem(
    modifier: Modifier = Modifier,
    id: Long,
    title: String,
    description: String,
    categoriesText: String,
    createdDateText: String,
    contentAssignee: ContentAssignee,
    onClickMemoItem: (Long) -> Unit,
) {
    val isTitleOrDescriptionEmpty = title.isEmpty() || description.isEmpty()
    val subText = description.replace(lineBreakRegex, " ")
    val mainText = title.ifEmpty {subText }

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(all = CaramelTheme.spacing.xl)
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = { onClickMemoItem(id) },
                ),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = mainText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = CaramelTheme.typography.body2.bold,
            color = CaramelTheme.color.text.primary,
        )
        if (!isTitleOrDescriptionEmpty) {
            Spacer(modifier = Modifier.height(CaramelTheme.spacing.xs))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = subText,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = CaramelTheme.typography.body2.regular,
                color = CaramelTheme.color.text.primary,
            )
        }
        Spacer(modifier = Modifier.height(CaramelTheme.spacing.s))
        TabMetaData(
            contentAssignee = contentAssignee,
            createdDateText = createdDateText,
            categoriesText = categoriesText,
        )
    }
}

@Composable
internal fun TabMetaData(
    contentAssignee: ContentAssignee,
    createdDateText: String,
    categoriesText: String,
) {
    val memoText =
        stringResource(
            when (contentAssignee) {
                ContentAssignee.ME -> Res.string.my_memo
                ContentAssignee.PARTNER -> Res.string.partner_memo
                ContentAssignee.US -> Res.string.both_memo
            },
        )

    @Composable
    fun Dot() {
        Box(
            modifier =
                Modifier
                    .size(size = 2.dp)
                    .clip(CircleShape)
                    .background(color = CaramelTheme.color.text.secondary),
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.xs),
    ) {
        Text(
            text = memoText,
            style = CaramelTheme.typography.label1.regular,
            color = CaramelTheme.color.text.secondary,
        )
        Dot()
        Text(
            text = createdDateText,
            style = CaramelTheme.typography.label1.regular,
            color = CaramelTheme.color.text.secondary,
        )
        if (categoriesText.isNotEmpty()) {
            Dot()
            Text(
                text = categoriesText,
                maxLines = 1,
                style = CaramelTheme.typography.label1.regular,
                color = CaramelTheme.color.text.secondary,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
internal fun EmptyMemo(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
        ) {
            Icon(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                painter = painterResource(Resources.Image.img_blank_memo),
                tint = Color.Unspecified,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.size(CaramelTheme.spacing.l))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(Res.string.empty_memo),
                style = CaramelTheme.typography.body3.regular,
                color = CaramelTheme.color.text.primary,
                textAlign = TextAlign.Center,
            )
        }
    }
}
