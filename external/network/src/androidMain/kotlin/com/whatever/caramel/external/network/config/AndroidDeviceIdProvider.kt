package com.whatever.caramel.external.network.config

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.whatever.caramel.external.network.config.DeviceIdProvider

class AndroidDeviceIdProvider(
    private val context: Context,
) : DeviceIdProvider {
    override val deviceId: String
        @SuppressLint("HardwareIds")
        get() = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}
