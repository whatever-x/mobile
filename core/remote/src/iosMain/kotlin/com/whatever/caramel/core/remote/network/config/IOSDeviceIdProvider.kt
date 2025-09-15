package com.whatever.caramel.core.remote.network.config

import keychainHelperBridge.KeychainHelperBridge
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
class IOSDeviceIdProvider(
    private val keychainHelperBridge: KeychainHelperBridge,
) : DeviceIdProvider {
    override val deviceId: String
        get() {
            keychainHelperBridge.getWithKey(key = KEY)?.let { value ->
                return value
            }

            val newId = platform.Foundation.NSUUID().UUIDString
            keychainHelperBridge.set(key = KEY, value = newId)

            return newId
        }

    companion object {
        private const val KEY = "com.whatever.caramel"
    }
}
