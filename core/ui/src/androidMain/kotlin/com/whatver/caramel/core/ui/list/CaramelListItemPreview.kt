package com.whatver.caramel.core.ui.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.vectorResource

@Preview
@Composable
private fun CaramelListItemPreview(
    @PreviewParameter(CaramelListItemPreviewProvider::class) data: CaramelListViewPreviewData
) {
    CaramelTheme {
        CaramelListItem(
            content = {
                Text(
                    text = data.content,
                    style = CaramelTheme.typography.body2.regular,
                    color = CaramelTheme.color.text.primary
                )
            },
            leading = {
                data.leadingIcon?.let {
                    Icon(
                        modifier = Modifier
                            .padding(end = 10.dp),
                        imageVector = vectorResource(it),
                        contentDescription = null
                    )
                }

            },
            trailing = {
                if (data.trailingIconVisible) {
                    TrailingArrow()
                } else if (!data.trailingText.isNullOrEmpty()) {
                    TrailingText(text = data.trailingText)
                }
            }
        )
    }
}

@Composable
private fun TrailingText(text: String) {
    Text(
        text = text,
        style = CaramelTheme.typography.body3.regular,
        color = CaramelTheme.color.text.accent
    )
}

@Composable
private fun TrailingArrow() {
    Icon(
        imageVector = vectorResource(Resources.Icon.arrow_right),
        contentDescription = null
    )
}