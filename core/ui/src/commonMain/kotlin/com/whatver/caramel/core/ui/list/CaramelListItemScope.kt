package com.whatver.caramel.core.ui.list

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector

@Stable
interface CaramelListItemScope {
    fun leadingIcon(icon: ImageVector)
    fun trailingArrow()
    fun trailingText(text : String)
}

class DefaultCaramelListItemScope : CaramelListItemScope {
    var leadingIcon: ImageVector? = null
    var hasTrailingArrow: Boolean = false
    var trailingText: String? = null

    override fun leadingIcon(icon: ImageVector) {
        leadingIcon = icon
    }

    override fun trailingArrow() {
        hasTrailingArrow = true
    }

    override fun trailingText(text: String) {
        trailingText = text
    }
}