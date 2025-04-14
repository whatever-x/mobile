package com.whatever.caramel.core.designsystem.components

import androidx.compose.runtime.Stable

@Stable
interface CaramelDialogScope {
    val onDismissRequest: () -> Unit

    val title : String
    val message : String?

    val mainButtonText : String
    val onMainButtonClick : () -> Unit

    val subButtonText : String?
    val onSubButtonClick : (() -> Unit)?
}

class CaramelDefaultDialogScope(
    override val onDismissRequest: () -> Unit,
    override val title: String,
    override val message: String?,
    override val mainButtonText: String,
    override val onMainButtonClick: () -> Unit,
    override val subButtonText: String?,
    override val onSubButtonClick: (() -> Unit)?
) : CaramelDialogScope
