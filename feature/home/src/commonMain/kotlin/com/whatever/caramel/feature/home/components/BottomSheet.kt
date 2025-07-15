package com.whatever.caramel.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.CaramelButton
import com.whatever.caramel.core.designsystem.components.CaramelButtonSize
import com.whatever.caramel.core.designsystem.components.CaramelButtonType
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ShareMessageBottomSheet(
    modifier: Modifier = Modifier,
    bottomSheetContentModifier: Modifier = Modifier,
    sheetState: SheetState,
    shareMessage: String,
    messageLength: Int,
    onInputShareMessage: (String) -> Unit,
    onDismiss: () -> Unit,
    onClickSave: () -> Unit,
    onClearMessage: () -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        dragHandle = null,
        containerColor = CaramelTheme.color.background.tertiary,
        scrimColor = CaramelTheme.color.alpha.primary,
        sheetState = sheetState,
    ) {
        BottomSheetContent(
            modifier = bottomSheetContentModifier,
            shareMessage = shareMessage,
            messageLength = messageLength,
            onInputShareMessage = onInputShareMessage,
            onClickSave = onClickSave,
            onClearMessage = onClearMessage
        )
    }
}

@Composable
private fun BottomSheetContent(
    modifier: Modifier = Modifier,
    shareMessage: String,
    messageLength: Int,
    onInputShareMessage: (String) -> Unit,
    onClickSave: () -> Unit,
    onClearMessage: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(modifier = modifier) {
        BasicTextField(
            modifier = Modifier.focusRequester(focusRequester = focusRequester),
            value = shareMessage,
            onValueChange = { newValue -> onInputShareMessage(newValue)},
            minLines = 1,
            maxLines = 2,
            textStyle =
                CaramelTheme.typography.heading3.copy(
                    color = CaramelTheme.color.text.primary,
                ),
            cursorBrush = SolidColor(CaramelTheme.color.fill.brand),
        ) { innerTextField ->
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(height = 52.dp),
            ) {
                innerTextField()

                if (messageLength == 0) {
                    Text(
                        text = "기억하고 싶은 말을 남겨보세요",
                        style = CaramelTheme.typography.heading3,
                        color = CaramelTheme.color.text.placeholder,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(height = CaramelTheme.spacing.l))

        Text(
            text = "$messageLength/24",
            style = CaramelTheme.typography.body3.bold,
            color = CaramelTheme.color.text.placeholder,
        )

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = CaramelTheme.spacing.xl),
            horizontalArrangement =
                Arrangement.spacedBy(
                    space = CaramelTheme.spacing.m,
                ),
        ) {
            CaramelButton(
                modifier = Modifier.weight(1f),
                buttonType = CaramelButtonType.Enabled2,
                buttonSize = CaramelButtonSize.Large,
                text = "비우기",
                onClick = onClearMessage,
            )

            CaramelButton(
                modifier = Modifier.weight(1f),
                buttonType = CaramelButtonType.Enabled1,
                buttonSize = CaramelButtonSize.Large,
                text = "저장하기",
                onClick = onClickSave,
            )
        }
    }
}
