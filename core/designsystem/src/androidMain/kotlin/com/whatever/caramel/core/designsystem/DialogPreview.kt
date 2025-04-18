package com.whatever.caramel.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.designsystem.components.CaramelDialog
import com.whatever.caramel.core.designsystem.components.DefaultCaramelDialogLayout
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

data class CaramelDialogPreviewData(
    val title: String,
    val message: String? = null,
    val mainButtonText: String,
    val subButtonText: String? = null,
    val mainButtonClickEvent: () -> Unit,
    val subButtonClickEvent: (() -> Unit)? = null,
)

class CaramelDialogPreviewProvider : PreviewParameterProvider<CaramelDialogPreviewData> {
    override val values: Sequence<CaramelDialogPreviewData>
        get() = sequenceOf(
            CaramelDialogPreviewData(
                title = "Default Title",
                message = "This is a default message",
                mainButtonText = "OK",
                subButtonText = "Cancel",
                mainButtonClickEvent = {},
                subButtonClickEvent = {}
            ),
            CaramelDialogPreviewData(
                title = "Default Title",
                message = "This is a default message",
                mainButtonText = "OK",
                mainButtonClickEvent = {}
            ),
            CaramelDialogPreviewData(
                title = "Default Title",
                mainButtonText = "OK",
                subButtonText = "Cancel",
                mainButtonClickEvent = {}
            ),
            CaramelDialogPreviewData(
                title = "Default Title",
                mainButtonText = "OK",
                subButtonText = "Cancel",
                mainButtonClickEvent = {},
                subButtonClickEvent = {}
            )
        )
}

@Preview
@Composable
private fun CaramelDialogPreview(
    @PreviewParameter(CaramelDialogPreviewProvider::class) data: CaramelDialogPreviewData,
) {
    CaramelTheme {
        CaramelDialog(
            show = true,
            onDismissRequest = {},
            title = data.title,
            message = data.message,
            mainButtonText = data.mainButtonText,
            subButtonText = data.subButtonText,
            onSubButtonClick = data.subButtonClickEvent,
            onMainButtonClick = data.mainButtonClickEvent
        ) {
            DefaultCaramelDialogLayout()
        }
    }
}