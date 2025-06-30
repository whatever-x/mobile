package com.whatever.caramel.core.remote.network.config

import platform.UIKit.UIDevice

class IOSDeviceIdProvider : DeviceIdProvider {
    override val deviceId = UIDevice.currentDevice.identifierForVendor?.UUIDString ?: "unknown"
}
