package com.whatever.core.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatver.caramel.core.ui.list.CaramelListItem


@Preview(showBackground = true)
@Composable
private fun CaramelListItemPreview(
    @PreviewParameter(CaramelListItemPreviewProvider::class) data : CaramelListViewPreviewData
) {
    CaramelTheme {
        CaramelListItem(
            text = data.content,
        ) {
            if(data.trailingIconVisible){
                trailingArrow()
            }
            data.trailingText?.let {
                trailingText(text = it)
            }
            data.leadingIcon?.let {
                leadingIcon(icon = it)
            }
        }
    }
}