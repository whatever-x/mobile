package com.whatever.caramel.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Stable
interface CaramelDialogScope {
    val onDismissRequest: () -> Unit

    val title: String
    val message: String?

    val mainButtonText: String
    val onMainButtonClick: () -> Unit

    val subButtonText: String?
    val onSubButtonClick: (() -> Unit)?
}

class CaramelDefaultDialogScope(
    override val onDismissRequest: () -> Unit,
    override val title: String,
    override val message: String?,
    override val mainButtonText: String,
    override val onMainButtonClick: () -> Unit,
    override val subButtonText: String?,
    override val onSubButtonClick: (() -> Unit)?,
) : CaramelDialogScope

@Composable
fun CaramelDialog(
    show: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    message: String? = null,
    onSubButtonClick: (() -> Unit)? = null,
    subButtonText: String? = null,
    mainButtonText: String,
    onMainButtonClick: () -> Unit,
    content: @Composable CaramelDialogScope.() -> Unit,
) {
    if (!show) return

    val scope = remember(
        onDismissRequest,
        title,
        message,
        onSubButtonClick,
        subButtonText,
        mainButtonText,
        onMainButtonClick
    ) {
        CaramelDefaultDialogScope(
            onDismissRequest = onDismissRequest,
            title = title,
            message = message,
            mainButtonText = mainButtonText,
            onMainButtonClick = onMainButtonClick,
            subButtonText = subButtonText,
            onSubButtonClick = onSubButtonClick
        )
    }
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier.wrapContentHeight(),
            shape = CaramelTheme.shape.l,
            color = CaramelTheme.color.background.tertiary
        ) {
            scope.content()
        }
    }
}

@Composable
fun CaramelDialogScope.DefaultCaramelDialogLayout() {
    val hasMessage = !message.isNullOrBlank()
    val hasSubButton = !subButtonText.isNullOrBlank() && onSubButtonClick != null
    Column(
        modifier = Modifier.padding(all = CaramelTheme.spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CaramelDialogTitle()
        if (hasMessage) {
            Spacer(Modifier.padding(bottom = CaramelTheme.spacing.xs))
            CaramelDialogContent()
        }
        Spacer(Modifier.padding(bottom = CaramelTheme.spacing.xl))
        if (hasSubButton) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.s),
            ) {
                CaramelDialogMainButton(modifier = Modifier.weight(1f))
                CaramelDialogSubButton(modifier = Modifier.weight(1f))
            }
        } else {
            CaramelDialogMainButton(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun CaramelDialogScope.CaramelDialogTitle(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = this.title,
        style = CaramelTheme.typography.body1.bold,
        color = CaramelTheme.color.text.primary
    )
}

@Composable
fun CaramelDialogScope.CaramelDialogContent(
    modifier: Modifier = Modifier,
) {
    val dialogMessage = this.message ?: return
    Text(
        modifier = modifier.fillMaxWidth(),
        text = dialogMessage,
        style = CaramelTheme.typography.body2.regular,
        color = CaramelTheme.color.text.primary
    )
}

@Composable
fun CaramelDialogScope.CaramelDialogMainButton(
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 11.dp),
        onClick = this.onMainButtonClick,
        colors = ButtonColors(
            containerColor = CaramelTheme.color.fill.brand,
            contentColor = CaramelTheme.color.text.inverse,
            disabledContentColor = CaramelTheme.color.text.disabledPrimary,
            disabledContainerColor = CaramelTheme.color.fill.disabledPrimary
        ),
        shape = CaramelTheme.shape.xxl
    ) {
        Text(
            text = mainButtonText,
            style = CaramelTheme.typography.body3.bold
        )
    }
}

@Composable
fun CaramelDialogScope.CaramelDialogSubButton(
    modifier: Modifier = Modifier,
) {
    val subButtonText = this.subButtonText ?: return
    val subButtonClickEvent = this.onSubButtonClick ?: return

    Button(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 11.dp),
        onClick = subButtonClickEvent,
        colors = ButtonColors(
            containerColor = CaramelTheme.color.fill.quinary,
            contentColor = CaramelTheme.color.text.brand,
            disabledContentColor = CaramelTheme.color.text.disabledPrimary,
            disabledContainerColor = CaramelTheme.color.fill.disabledPrimary
        ),
        shape = CaramelTheme.shape.xxl
    ) {
        Text(
            text = subButtonText,
            style = CaramelTheme.typography.body3.bold
        )
    }
}