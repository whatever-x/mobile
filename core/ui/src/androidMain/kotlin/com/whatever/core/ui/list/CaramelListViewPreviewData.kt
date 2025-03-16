package com.whatever.core.ui.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal data class CaramelListViewPreviewData(
    val content : String,
    val leadingIcon : ImageVector? = null,
    val trailingIconVisible: Boolean = false,
    val trailingText : String? = null
)

internal class CaramelListItemPreviewProvider : PreviewParameterProvider<CaramelListViewPreviewData> {
    override val values: Sequence<CaramelListViewPreviewData> = sequenceOf(
        CaramelListViewPreviewData(
            content = "Text"
        ),
        CaramelListViewPreviewData(
            content = "Text",
            trailingText = "Button"
        ),
        CaramelListViewPreviewData(
            content = "Text",
            trailingIconVisible = true
        ),
        CaramelListViewPreviewData(
            content = "Text",
            leadingIcon = Icons.Filled.Favorite,
        ),
        CaramelListViewPreviewData(
            content = "Text",
            leadingIcon = Icons.Filled.Favorite,
            trailingIconVisible = true
        ),
        CaramelListViewPreviewData(
            content = "Text",
            leadingIcon = Icons.Filled.Favorite,
            trailingText = "Button"
        ),
    )

}