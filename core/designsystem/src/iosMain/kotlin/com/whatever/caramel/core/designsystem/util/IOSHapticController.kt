package com.whatever.caramel.core.designsystem.util

import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle

class IOSHapticController : HapticController {
    override fun performImpact(style: HapticStyle) {
        val generator =
            when (style) {
                HapticStyle.GestureThresholdActivate -> UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleMedium)
            }

        generator.prepare()
        generator.impactOccurred()
    }
}
