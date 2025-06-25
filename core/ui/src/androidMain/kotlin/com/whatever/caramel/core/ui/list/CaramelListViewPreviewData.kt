package com.whatever.caramel.core.ui.list

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.designsystem.foundations.Resources
import org.jetbrains.compose.resources.DrawableResource

internal data class CaramelListViewPreviewData(
    val content : String,
    val leadingIcon : DrawableResource? = null,
    val trailingIconVisible: Boolean = false,
    val trailingText : String? = null
)

internal class CaramelListItemPreviewProvider :
    PreviewParameterProvider<CaramelListViewPreviewData> {
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
            leadingIcon = Resources.Icon.ic_profile_20,
        ),
        CaramelListViewPreviewData(
            content = "Text",
            leadingIcon = Resources.Icon.ic_profile_20,
            trailingIconVisible = true
        ),
        CaramelListViewPreviewData(
            content = "Text",
            leadingIcon = Resources.Icon.ic_profile_20,
            trailingText = "Button"
        ),
    )

}