package com.whatever.caramel.core.designsystem.util

import androidx.compose.runtime.Composable
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UIModalPresentationOverCurrentContext

@Composable
actual fun DisableDialogDim() {
    val viewController = UIApplication.sharedApplication.keyWindow?.rootViewController ?: return
    viewController.modalPresentationStyle = UIModalPresentationOverCurrentContext

    val view = viewController.view
    view.backgroundColor = UIColor.clearColor

    NSLayoutConstraint.activateConstraints(
        listOf(
            view.leadingAnchor.constraintEqualToAnchor(view.leadingAnchor()),
            view.trailingAnchor.constraintEqualToAnchor(view.trailingAnchor()),
            view.bottomAnchor.constraintEqualToAnchor(view.safeAreaLayoutGuide.bottomAnchor()),
        ),
    )
}
