package com.whatever.caramel.core.designsystem.util

/**
 * 각 플랫폼에서 제공하는 햅틱 피드백을 제어하는 인터페이스입니다.
 * @author GunHyung-Ham
 * @since 2025.04.02
 */
interface HapticController {

    fun performImpact(style: HapticStyle)

}

enum class HapticStyle {
    GestureThresholdActivate
}