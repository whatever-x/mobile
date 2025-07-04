package com.whatever.caramel.core.remote.network.config

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

class AndroidDeviceIdProvider(
    private val context: Context,
) : DeviceIdProvider {
    override val deviceId: String
        @SuppressLint("HardwareIds")
        get() = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}
