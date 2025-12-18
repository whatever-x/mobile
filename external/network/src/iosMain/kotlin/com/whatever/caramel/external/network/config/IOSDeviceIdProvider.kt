package com.whatever.caramel.external.network.config

import com.whatever.caramel.external.network.config.DeviceIdProvider
import keychainHelperBridge.KeychainHelperBridge
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSUUID

@OptIn(ExperimentalForeignApi::class)
class IOSDeviceIdProvider(
    private val keychainHelperBridge: KeychainHelperBridge,
) : DeviceIdProvider {
    override val deviceId: String
        get() {
            keychainHelperBridge.getWithKey(key = KEY)?.let { value ->
                return value
            }

            val newId = NSUUID().UUIDString
            keychainHelperBridge.set(key = KEY, value = newId)

            return newId
        }

    companion object {
        private const val KEY = "com.whatever.caramel"
    }
}
