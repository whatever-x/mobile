package com.whatever.caramel.core.designsystem.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

class AndroidHapticController(private val context: Context) : HapticController {

    @SuppressLint("MissingPermission")
    override fun performImpact(style: HapticStyle) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        val duration = when (style) {
            HapticStyle.GestureThresholdActivate -> 10L
        }

        val amplitude = when (style) {
            HapticStyle.GestureThresholdActivate -> 30
        }

        vibrator.vibrate(
            VibrationEffect.createOneShot(duration, amplitude)
        )
    }

}