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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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
    initialShareMessage: String,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onClickSave: (String) -> Unit,
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
            initialShareMessage = initialShareMessage,
            onClickSave = { newShareMessage -> onClickSave(newShareMessage) },
        )
    }
}

fun countGraphemeClusters(text: String): Int = Regex("\\X").findAll(text).count()

@Composable
private fun BottomSheetContent(
    modifier: Modifier = Modifier,
    initialShareMessage: String,
    onClickSave: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var newShareMessage by remember {
        mutableStateOf(
            TextFieldValue(
                text = initialShareMessage,
                selection = TextRange(initialShareMessage.length),
            ),
        )
    }
    val graphemeCount =
        remember(newShareMessage.text) {
            countGraphemeClusters(newShareMessage.text)
        }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(modifier = modifier) {
        BasicTextField(
            modifier = Modifier.focusRequester(focusRequester = focusRequester),
            value = newShareMessage,
            onValueChange = { newValue ->
                val graphemeCount = countGraphemeClusters(newValue.text)
                if (!newValue.text.contains("\n") && graphemeCount <= 24) {
                    newShareMessage = newValue
                }
            },
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

                if (newShareMessage.text.isEmpty()) {
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
            text = "$graphemeCount/24",
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
                onClick = {
                    newShareMessage =
                        TextFieldValue(selection = TextRange(initialShareMessage.length))
                },
            )

            CaramelButton(
                modifier = Modifier.weight(1f),
                buttonType = CaramelButtonType.Enabled1,
                buttonSize = CaramelButtonSize.Large,
                text = "저장하기",
                onClick = {
                    keyboardController?.hide()
                    onClickSave(newShareMessage.text)
                },
            )
        }
    }
}
