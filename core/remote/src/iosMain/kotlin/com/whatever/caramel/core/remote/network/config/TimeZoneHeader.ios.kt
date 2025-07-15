package com.whatever.caramel.core.remote.network.config

import platform.Foundation.localTimeZone

actual fun currentTimeZone(): String =
    platform.Foundation.NSTimeZone.localTimeZone.name